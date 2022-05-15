package com.example.dianping.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.DecayPlacement;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionBoostMode;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScore;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest.Builder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.dianping.mapper.ShopMapper;
import com.example.dianping.model.Category;
import com.example.dianping.model.Seller;
import com.example.dianping.model.Shop;
import com.example.dianping.sdk.AlgorithmRecommendStub;
import com.example.dianping.service.CategoryService;
import com.example.dianping.service.SellerService;
import com.example.dianping.service.ShopService;

/**
 * ShopServiceImpl
 *
 * @author xuyanpeng01 on 2022/4/21
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopServiceImpl implements ShopService {

    private final ShopMapper shopMapper;

    private final CategoryService categoryService;

    private final SellerService sellerService;

    private final ElasticsearchClient elasticsearchClient;

    private final AlgorithmRecommendStub algorithmRecommendStub;

    @Transactional
    @Override
    public Shop create(Shop shop) {
        try {
            checkSeller(shop.getSellerId());
            checkCategory(shop.getCategoryId());
            shopMapper.insert(shop);
            return get(shop.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("创建商户失败");
        }
    }

    @Override
    public Shop get(int id) {
        Shop shop = shopMapper.selectById(id);
        shop.setSeller(sellerService.get(shop.getSellerId()));
        shop.setCategory(categoryService.get(shop.getCategoryId()));
        return shop;
    }

    @Override
    public List<Shop> selectAll() {
        List<Shop> shops = shopMapper.selectList(new LambdaQueryWrapper<Shop>().orderByAsc(Shop::getId));
        shops.forEach(shop -> {
            shop.setSeller(sellerService.get(shop.getSellerId()));
            shop.setCategory(categoryService.get(shop.getCategoryId()));
        });
        return shops;
    }

    @Override
    public List<Shop> search(BigDecimal longitude, BigDecimal latitude, String keyword, String categoryId) {
        return searchFromEs(longitude, latitude, keyword, categoryId);
    }

    @Override
    public List<Shop> getShopList(int categoryId) {
        if (Objects.isNull(categoryId)) {
            return selectAll();
        }
        List<Shop> shops = shopMapper.selectList(new LambdaQueryWrapper<Shop>()
            .eq(Shop::getCategoryId, categoryId)
            .orderByAsc(Shop::getId));
        shops.forEach(shop -> {
            shop.setSeller(sellerService.get(shop.getSellerId()));
            shop.setCategory(categoryService.get(shop.getCategoryId()));
        });
        return shops;
    }

    @Override
    public List<Shop> recommend(int userId) {
        List<Integer> shopIdList = algorithmRecommendStub.recommend(userId);
        // 只取前 10 个
        shopIdList = shopIdList.subList(0, 10);

        List<Shop> shops = shopMapper.selectList(new LambdaQueryWrapper<Shop>()
            .in(Shop::getId, shopIdList));
        shops.forEach(shop -> {
            shop.setSeller(sellerService.get(shop.getSellerId()));
            shop.setCategory(categoryService.get(shop.getCategoryId()));
        });
        return shops;
    }

    private void checkSeller(Integer id) {
        Seller seller = sellerService.get(id);
        if (seller == null) {
            throw new RuntimeException("商户不存在");
        }
        if (seller.getDisabledFlag() == 1) {
            throw new RuntimeException("商户已被禁用");
        }
    }

    private void checkCategory(Integer id) {
        Category category = categoryService.get(id);
        if (category == null) {
            throw new RuntimeException("类目不存在");
        }
    }


    List<Shop> searchFromEs(BigDecimal longitude, BigDecimal latitude, String keyword, String categoryId) {
        try {
            List<Shop> shops = new ArrayList<>();
            // 根据条件从 ES 中搜索对应的 shop id
            SearchRequest searchRequest = buildSmartSearchRequest(longitude, latitude, keyword, categoryId);

            SearchResponse<Map> response = elasticsearchClient.search(searchRequest, Map.class);
            List<Hit<Map>> hitList = response.hits().hits();

            hitList.forEach(e -> {
                // 从 MySQL 中搜索详细信息
                Shop shop = get(Integer.valueOf(e.id()));
                String distance = e.fields().get("distance").toJson().asJsonArray().get(0).toString();
                shop.setDistance(new BigDecimal(distance));

                shops.add(shop);
            });

            return shops;
        } catch (IOException e) {
            throw new RuntimeException("SearchFromEs failed.");
        }
    }

    private SearchRequest buildSmartSearchRequest(BigDecimal longitude, BigDecimal latitude, String keyword,
        String categoryId) {
        // FunctionScores
        List<FunctionScore> functionScores = new ArrayList<>();

        FunctionScore locationFunctionScore = new FunctionScore.Builder()
            .gauss(g -> g.field("location")
                .placement(DecayPlacement.of(d -> d
                    .origin(JsonData.of(latitude + "," + longitude))
                    .scale(JsonData.of("100km"))
                    .offset(JsonData.of("0km"))
                    .decay(0.5d))))
            .weight(9d)
            .build();

        FunctionScore remarkScoreFunctionScore = new FunctionScore.Builder()
            .fieldValueFactor(f -> f
                .field("remark_score"))
            .weight(0.2d)
            .build();

        FunctionScore sellerRemarkScoreFunctionScore = new FunctionScore.Builder()
            .fieldValueFactor(f -> f
                .field("seller_remark_score"))
            .weight(0.1d)
            .build();

        functionScores.add(locationFunctionScore);
        functionScores.add(remarkScoreFunctionScore);
        functionScores.add(sellerRemarkScoreFunctionScore);

        // Script field
        Map<String, JsonData> distanceScriptParams = new HashMap<>();
        distanceScriptParams.put("lat", JsonData.of(latitude));
        distanceScriptParams.put("lon", JsonData.of(longitude));


        // Query
        BoolQuery.Builder builder = new BoolQuery.Builder();
        builder.must(TermQuery.of(t -> t.field("seller_disabled_flag").value(0))._toQuery());
        if (Objects.nonNull(keyword)) {
            builder.must(MatchQuery.of(m -> m.field("name").query(keyword).boost(0.1f))._toQuery());
        }
        if (Objects.nonNull(categoryId)) {
            builder.must(TermQuery.of(t -> t.field("category_id").value(categoryId))._toQuery());
        }
        BoolQuery boolQuery = builder.build();

        return new Builder().index("shop")
            .scriptFields("distance", d -> d
                .script(s -> s.inline(f -> f
                    .source("haversin(lat,lon,doc['location'].lat,doc['location'].lon)")
                    .lang("expression")
                    .params(distanceScriptParams))))
            .query(q -> q
                .functionScore(f -> f
                    .query(q1 -> q1
                        .bool(boolQuery))
                    .functions(functionScores)
                    .scoreMode(FunctionScoreMode.Sum)
                    .boostMode(FunctionBoostMode.Sum)))
            .build();
    }
}

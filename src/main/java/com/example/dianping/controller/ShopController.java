package com.example.dianping.controller;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dianping.common.CommonRs;
import com.example.dianping.model.Shop;
import com.example.dianping.request.CreateShopRequest;
import com.example.dianping.service.ShopService;

/**
 * ShopController
 *
 * @author xuyanpeng01 on 2022/4/21
 */
@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public CommonRs getShopList(
        @RequestParam(required = false) Integer categoryId,
        @RequestParam(defaultValue = "1") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize) {
        return CommonRs.success(shopService.getShopList(categoryId, pageNum, pageSize));
    }

    @PostMapping
    public CommonRs createShop(@RequestBody CreateShopRequest createShopRequest) {
        try {
            Shop shop = new Shop();
            shop.setIconUrl(createShopRequest.getIconUrl());
            shop.setAddress(createShopRequest.getAddress());
            shop.setCategoryId(createShopRequest.getCategoryId());
            shop.setEndTime(createShopRequest.getEndTime());
            shop.setStartTime(createShopRequest.getStartTime());
            shop.setLongitude(createShopRequest.getLongitude());
            shop.setLatitude(createShopRequest.getLatitude());
            shop.setName(createShopRequest.getName());
            shop.setPricePerMan(createShopRequest.getPricePerMan());
            shop.setSellerId(createShopRequest.getSellerId());

            return CommonRs.success(shopService.create(shop));
        } catch (Exception e) {
            return CommonRs.fail(null, "创建店铺失败");
        }
    }

    @GetMapping("/recommend")
    public CommonRs recommend(@RequestParam int userId) {
        List<Shop> result = shopService.recommend(userId);
        return CommonRs.success(result);
    }

    @GetMapping("/search")
    public CommonRs search(
        @RequestParam BigDecimal longitude,
        @RequestParam BigDecimal latitude,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String categoryId,
        @RequestParam(defaultValue = "1") int pageNum,
        @RequestParam(defaultValue = "10") int pageSize) {
        return CommonRs.success(shopService.search(longitude, latitude, keyword, categoryId, pageNum, pageSize));
    }
}

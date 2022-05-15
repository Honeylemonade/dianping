package com.example.dianping.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.dianping.common.CommonRs;
import com.example.dianping.model.Shop;
import com.github.pagehelper.PageInfo;

public interface ShopService {

    Shop create(Shop shop);

    Shop get(int id);

    List<Shop> selectAll();

    PageInfo<Shop> search(BigDecimal longitude, BigDecimal latitude, String keyword, String categoryId, int pageNum,
        int pageSize);

    PageInfo<Shop> getShopList(Integer categoryId, int pageNum, int pageSize);

    List<Shop> recommend(int userId);
}

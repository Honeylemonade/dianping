package com.example.dianping.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.dianping.common.CommonRs;
import com.example.dianping.model.Shop;

public interface ShopService {

    Shop create(Shop shop);

    Shop get(int id);

    List<Shop> selectAll();

    List<Shop> search(BigDecimal longitude, BigDecimal latitude, String keyword, String categoryId);

    List<Shop> getShopList(int categoryId);

    List<Shop> recommend(int userId);
}

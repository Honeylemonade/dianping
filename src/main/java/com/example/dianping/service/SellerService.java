package com.example.dianping.service;

import java.util.List;

import com.example.dianping.model.Seller;
import com.github.pagehelper.PageInfo;

/**
 * SellerService
 *
 * @author xuyanpeng01 on 2022/4/19
 */
public interface SellerService {

    Seller create(Seller seller);

    Seller get(Integer id);

    PageInfo<Seller> selectList(int pageNum, int pageSize);

    Seller changeStatus(Integer id, Integer disabledFlag);
}

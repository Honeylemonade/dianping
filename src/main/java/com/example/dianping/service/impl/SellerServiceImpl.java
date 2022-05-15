package com.example.dianping.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.dianping.mapper.SellerMapper;
import com.example.dianping.model.Seller;
import com.example.dianping.service.SellerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * SellerServiceImpl
 *
 * @author xuyanpeng01 on 2022/4/19
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SellerServiceImpl implements SellerService {

    private final SellerMapper sellerMapper;

    @Override
    public Seller create(Seller seller) {
        sellerMapper.insert(seller);
        return sellerMapper.selectById(seller.getId());
    }

    @Override
    public Seller get(Integer id) {
        return sellerMapper.selectById(id);
    }

    @Override
    public PageInfo<Seller> selectList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Seller> sellers = sellerMapper.selectList(null);
        PageInfo<Seller> sellerPageInfo = new PageInfo<>(sellers);
        return sellerPageInfo;
    }

    @Override
    public Seller changeStatus(Integer id, Integer disabledFlag) {
        LambdaUpdateWrapper<Seller> updateWrapper = new LambdaUpdateWrapper<Seller>()
            .eq(Seller::getId, id)
            .set(Seller::getDisabledFlag, disabledFlag);

        sellerMapper.update(null, updateWrapper);
        return get(id);
    }
}

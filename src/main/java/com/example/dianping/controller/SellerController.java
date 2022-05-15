package com.example.dianping.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dianping.common.CommonRs;
import com.example.dianping.model.Seller;
import com.example.dianping.request.CreateSellerRequest;
import com.example.dianping.service.SellerService;

/**
 * SellerController
 *
 * @author xuyanpeng01 on 2022/4/19
 */
@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SellerController {

    private final SellerService sellerService;

    @GetMapping
    public CommonRs getAll(@RequestParam(defaultValue = "1") int pageNum,
        @RequestParam(defaultValue = "2") int pageSize) {
        return CommonRs.success(sellerService.selectList(pageNum, pageSize));
    }

    @PostMapping
    public CommonRs createSeller(@RequestBody CreateSellerRequest createSellerRequest) {
        try {
            Seller seller = Seller.builder().name(createSellerRequest.getName()).build();
            Seller result = sellerService.create(seller);
            return CommonRs.success(result);
        } catch (Exception e) {
            return CommonRs.fail(null, "创建商户失败");
        }
    }

    @PutMapping("/up")
    public CommonRs up(@RequestParam int id) {
        Seller seller = sellerService.changeStatus(id, 0);
        return CommonRs.success(seller);
    }

    @PutMapping("/down")
    public CommonRs down(@RequestParam int id) {
        Seller seller = sellerService.changeStatus(id, 1);
        return CommonRs.success(seller);
    }
}

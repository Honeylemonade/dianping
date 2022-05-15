package com.example.dianping.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dianping.common.CommonRs;
import com.example.dianping.service.GeoService;

/**
 * @author xuyanpeng01 on 2022/4/21
 */
@Slf4j
@RestController
@RequestMapping("/geo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GeoController {

    private final GeoService geoService;

    @GetMapping
    public CommonRs analysisAddress(@RequestParam String address) {
        // TODO
        return CommonRs.success(geoService.analysisAddress(address));
    }
}

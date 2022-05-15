package com.example.dianping.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dianping.model.Position;
import com.example.dianping.sdk.BaiduGeoClient;
import com.example.dianping.service.GeoService;

/**
 * @author xuyanpeng01 on 2022/4/21
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GeoServiceImpl implements GeoService {

    private final BaiduGeoClient baiduGeoClient;

    @Override
    public Position analysisAddress(String address) {
        return baiduGeoClient.analysisAddress(address);
    }
}

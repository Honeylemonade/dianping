package com.example.dianping.service;

import com.example.dianping.model.Position;

public interface GeoService {

    Position analysisAddress(String address);
}

package com.example.dianping.sdk;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.dianping.proto.DianPingGrpc;
import com.example.dianping.proto.DianPingGrpc.DianPingBlockingStub;
import com.example.dianping.proto.RecommendReply;
import com.example.dianping.proto.RecommendRequest;

/**
 * AlgorithmRecommend stub
 *
 * @author xuyanpeng01 on 2022/5/11
 */
@Component
public class AlgorithmRecommendStub {

    @Value("${algorithm.server.host}")
    private String algorithmServerHost;

    @Value("${algorithm.server.port}")
    private int algorithmServerPort;

    private DianPingBlockingStub stub;

    @PostConstruct
    public void init() {
        ManagedChannel channel = ManagedChannelBuilder
            .forAddress(algorithmServerHost, algorithmServerPort)
            .usePlaintext()
            .build();
        this.stub = DianPingGrpc.newBlockingStub(channel);

    }

    public List<Integer> recommend(int userId) {
        RecommendRequest request = RecommendRequest.newBuilder().setUserId(userId).build();

        RecommendReply reply = stub.recommend(request);

        return reply.getShopIdsList();
    }

}

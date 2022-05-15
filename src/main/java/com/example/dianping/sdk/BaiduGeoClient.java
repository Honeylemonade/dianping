package com.example.dianping.sdk;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.dianping.model.Position;

/**
 * @author xuyanpeng01 on 2022/4/21
 */
@Component
public class BaiduGeoClient {

    @Value("${baidu-geo.uri}")
    private String uri;

    @Value("${baidu-geo.ak}")
    private String ak;

    CloseableHttpClient client = HttpClients.custom()
        .setSSLSocketFactory(new SSLConnectionSocketFactory(
            SSLContexts.createSystemDefault(),
            new String[]{"TLSv1.2"},
            null,
            SSLConnectionSocketFactory.getDefaultHostnameVerifier()))
        .setConnectionTimeToLive(1, TimeUnit.MINUTES)
        .setDefaultSocketConfig(SocketConfig.custom()
            .setSoTimeout(5000)
            .build())
        .setDefaultRequestConfig(RequestConfig.custom()
            .setConnectTimeout(5000)
            .setSocketTimeout(5000)
            .setCookieSpec(CookieSpecs.STANDARD_STRICT)
            .build())
        .build();


    public Position analysisAddress(String address) {
        try {
            HttpGet httpGet = new HttpGet(uri);

            List params = new ArrayList();
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("output", "json"));
            params.add(new BasicNameValuePair("ak", ak));
            params.add(new BasicNameValuePair("callback", "showLocation"));

            URI uri = new URIBuilder(httpGet.getURI())
                .addParameters(params)
                .build();
            httpGet.setURI(uri);
            CloseableHttpResponse response = client.execute(httpGet);
            String bodyAsString = EntityUtils.toString(response.getEntity(),"UTF-8");
            client.close();
        } catch (Exception e) {

        } finally {


        }
        return null;
    }


}

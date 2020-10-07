package com.revature.controller;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Frontend.FrontendReq;
import com.revature.model.Initialization.InitializationResp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InitializationController {
    // private final String REQ_URL = "http://localhost:8080/testInit";
    // private final String REQ_URL = "http:// 10.100.122.66 /init/";
    private final String REQ_URL = "http://acefd179b3bf24f75a454ad47bab768e-575975828.us-east-1.elb.amazonaws.com/init/";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public InitializationResp runInitialization(FrontendReq frontendObj) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String result = objectMapper.writeValueAsString(frontendObj);
            RequestBody body = RequestBody.create(result, JSON);
            Request request = new Request.Builder().url(REQ_URL).post(body).build();
            OkHttpClient client = new OkHttpClient();
            Response response;
            try {
                response = client.newCall(request).execute();
                int respCode = response.code();
                if (respCode != 200) {
                    log.warn("Got a bad Response Code in testInitialization = " + respCode);
                    return new InitializationResp("failed");
                } else {
                    return new InitializationResp("done");
                }
            } catch (IOException e) {
                log.error("Failed setting response = " + e.getMessage());
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            log.error("Failed setting result = " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
package com.revature.controller;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpRequest {
    private final static Logger log = LoggerFactory.getLogger(HttpRequest.class);

    public static <T> Response sendHttpReq (T obj, String REQ_URL) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String result = objectMapper.writeValueAsString(obj);
            RequestBody body = RequestBody.create(result, JSON);
            Request request = new Request.Builder().url(REQ_URL).post(body).build();
            OkHttpClient client = new OkHttpClient();
            Response response;
            try {
                response = client.newCall(request).execute();
                return response;
            } catch (IOException e) {
                log.error("Failed setting response = " + e.getMessage());
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            log.error("Failed setting result = " + e.getMessage());
            e.printStackTrace();
        }

        // OkHttpClient client = new OkHttpClient();
        // String result;
        // try {
        //     result = objectMapper.writeValueAsString(obj);
        //     RequestBody body = RequestBody.create(result, JSON);
        //     Request request = new Request.Builder().url(REQ_URL).post(body).build();
    
        //     client.newBuilder().callTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).build();
        //     //.connectTimeout(180, TimeUnit.SECONDS)
        //     Response response;
        //     try {
        //         response = client.newCall(request).execute();
        //         return response;
        //     } catch (IOException e) {
        //         log.error("Failed setting response = " + e.getMessage());
        //     }
        // } catch (JsonProcessingException e1) {
        //     e1.printStackTrace();
        // }

        return null;
    }
}



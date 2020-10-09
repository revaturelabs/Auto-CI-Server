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


public class aMasterController <T> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Response sendHttpReq (T obj, String REQ_URL) {
//What would be the type of obj you would put into here?  Would we want another input for resp?
//Should we create like an abstract model class, and an abstract response class?
//URL input

    final MediaType JSON = MediaType.get("application/json; charset=utf-8");

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
        return null;
    }
}



package com.revature.controller;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Configuration.Configuration;
import com.revature.model.Configuration.ConfigurationResp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Config {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    final String URL = "http://localhost:8080/configtest2";
    // final String URL = "10.100.144.140:30100";
    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ConfigurationResp ConfigService(Configuration config) {

        log.info("Beginning ConfigService method");

        ObjectMapper mapper = new ObjectMapper();
        try {
            OkHttpClient client = new OkHttpClient();
            String json = mapper.writeValueAsString(config);
            RequestBody body = RequestBody.create(json, JSON);      
            Request request = new Request.Builder().url(URL).post(body).build();
            Response response; 
            try {
                response = client.newCall(request).execute();
                ConfigurationResp configresp;
                try {
                    configresp = mapper.readValue(response.body().byteStream(), ConfigurationResp.class);
                    return configresp;
                } catch (IOException e) {
                    log.error("Failed setting configresp = " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (IOException e) {
                log.error("Failed setting response = " + e.getMessage());
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            log.error("Failed setting jsonString = " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}

package com.revature.controller;

import java.io.IOException;

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

    public ConfigurationResp ConfigService(Configuration config) throws IOException {

        log.info("Beginning ConfigService method");

        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // create the JSON body
        String json = mapper.writeValueAsString(config);
        RequestBody body = RequestBody.create(json, JSON);      

        Request request = new Request.Builder().url(URL).post(body).build();

        Response response = client.newCall(request).execute();

        ConfigurationResp configresp = mapper.readValue(response.body().byteStream(), ConfigurationResp.class);

        //Process.setConfig("Done");

        return configresp;
    }

}

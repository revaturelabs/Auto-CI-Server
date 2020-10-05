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

    public void ConfigService(Configuration config) throws IOException {

        log.info("Beginning ConfigService method");

        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // create the JSON body
        // we're going to need a config object -- Configuration config = new Configuration(); 
        String json = mapper.writeValueAsString(config);
        RequestBody body = RequestBody.create(json, JSON);

        System.out.println("THIS SHOULD BE THE JSON: \n" + json);
        

        Request request = new Request.Builder().url("http://localhost:8080/configtest2").post(body).build();

        Response response = client.newCall(request).execute();

        ConfigurationResp configresp = mapper.readValue(response.body().byteStream(), ConfigurationResp.class);

        //Process.setConfig("Done");

        try {
        //JenkinsService.method(configresp)
        System.out.println("Got through the config method without issue maybe");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            //Process.setConfig("failed");
        }
    }

}

package com.revature.controller;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Jenkins.JenkinsServiceObject;
import com.revature.model.Jenkins.JenkinsServiceResp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Jenkins {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    final String URL = "http://localhost:8080/jenkinstest2";
    // final String URL = "http://10.100.77.80:30110";
    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public JenkinsServiceResp JenkinsService(JenkinsServiceObject jenkins) {
        log.info("Beginning JenkinsService method");
        ObjectMapper mapper = new ObjectMapper();
        try {
            OkHttpClient client = new OkHttpClient();
            String json = mapper.writeValueAsString(jenkins);
            RequestBody body = RequestBody.create(json, JSON);      
            Request request = new Request.Builder().url(URL).post(body).build();
            Response response;
            try {
                response = client.newCall(request).execute();
                JenkinsServiceResp jenkinsresp;
                try {
                    jenkinsresp = mapper.readValue(response.body().byteStream(), JenkinsServiceResp.class);
                    return jenkinsresp;
                } catch (IOException e) {
                    log.error("Failed setting jenkinsresp = " + e.getMessage());
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

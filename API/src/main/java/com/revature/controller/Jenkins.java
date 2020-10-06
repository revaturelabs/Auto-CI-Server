package com.revature.controller;

import java.io.IOException;

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

    public JenkinsServiceResp JenkinsService(JenkinsServiceObject jenkins) throws IOException {
        log.info("Beginning JenkinsService method");
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // create the JSON body
        String json = mapper.writeValueAsString(jenkins);
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder().url(URL).post(body).build();

        Response response = client.newCall(request).execute();

        JenkinsServiceResp jenkinsresp = mapper.readValue(response.body().byteStream(), JenkinsServiceResp.class);

        //Process.setJenkins("Done");

        return jenkinsresp;
    }

}

package com.revature.controller;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Spinnaker.SpinnakerServiceObject;
import com.revature.model.Spinnaker.SpinnakerServiceResp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SpinnakerController {
    private final String REQ_URL = "http://localhost:8080/testspinn";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public SpinnakerServiceResp testSpinnaker(SpinnakerServiceObject spinnObj) {

        ObjectMapper objectMapper = new ObjectMapper();
        
        try { // trying jsonString = 
            String jsonString = objectMapper.writeValueAsString(spinnObj);
            
            RequestBody body = RequestBody.create(jsonString, JSON);
            
            Request request = new Request.Builder().url(REQ_URL).post(body).build();
            
            OkHttpClient client = new OkHttpClient();

            Response response;
            try { // trying response = 
                response = client.newCall(request).execute();
                
                SpinnakerServiceResp spinnResp;
                try { // trying spinnResp = 
                    spinnResp = objectMapper.readValue(response.body().byteStream(), SpinnakerServiceResp.class);
                    
                    return spinnResp;
                } catch (IOException e) {
                    log.error("Failed setting spinnResp = " + e.getMessage());
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

package com.revature.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
    private final String REQ_URL = "https://localhost:8080/testspinn";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public HttpServletResponse testSpinnaker(HttpServletRequest spinnReq, HttpServletResponse resp)
            throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
        System.out.println("\tStarting testSpinnaker");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("\tWorking thru testSpinnaker ... 1");

        String json = objectMapper
                .writeValueAsString(objectMapper.readValue(spinnReq.getInputStream(), SpinnakerServiceObject.class));
                System.out.println("\tWorking thru testSpinnaker ... 2");

        RequestBody body = RequestBody.create(json, JSON);
        System.out.println("\tWorking thru testSpinnaker ... 3");

        Request request = new Request.Builder().url(REQ_URL).post(body).build();
        System.out.println("\tWorking thru testSpinnaker ... 4");

        OkHttpClient client = new OkHttpClient();
        System.out.println("\tWorking thru testSpinnaker ... 5");

        Response response = client.newCall(request).execute();
        System.out.println("\tWorking thru testSpinnaker ... 6");

        SpinnakerServiceResp spinnResp = objectMapper.readValue(response.body().byteStream(),
                SpinnakerServiceResp.class);
                System.out.println("\tWorking thru testSpinnaker ... 7");

        System.out.println(objectMapper.writeValueAsString(spinnResp));
        System.out.println("\tWorking thru testSpinnaker ... 8");

        String result = objectMapper.writeValueAsString(spinnResp);
        System.out.println("\tWorking thru testSpinnaker ... 9");
        PrintWriter out = resp.getWriter();
        System.out.println("\tWorking thru testSpinnaker ... 10");

        // return (print out for debugging)
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();

        System.out.println("\tEnding testSpinnaker");

        return resp;
    }
}

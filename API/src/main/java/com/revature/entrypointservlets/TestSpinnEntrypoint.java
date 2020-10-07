package com.revature.entrypointservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.SpinnakerController;
import com.revature.model.Spinnaker.SpinnakerServiceObject;
import com.revature.model.Spinnaker.SpinnakerServiceResp;

@WebServlet(name = "TestSpinnEntrypoint", urlPatterns = { "/testspinnentrypoint" })
public class TestSpinnEntrypoint extends HttpServlet{

    private static final long serialVersionUID = -2693027601970028768L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SpinnakerController sc = new SpinnakerController();

        //testing Spinnaker Controller
        SpinnakerServiceObject ssObj = new SpinnakerServiceObject();
        ssObj.setGitUri("gitUri.com");
        List<String> listProviders = new ArrayList<>();
        listProviders.add("aws");
        listProviders.add("kubernetes");
        ssObj.setCloudProviders(listProviders);
        ssObj.setEmail("true");
        ssObj.setProjectName("visualstudiocode");
        ssObj.setBranch("prod");

        SpinnakerServiceResp spinnResp = sc.testSpinnaker(ssObj);

        System.out.println("\n\tHere's this:" + ssObj + "\n");

        //sending results
        ObjectMapper objectMapper = new ObjectMapper();
        
        String result = objectMapper.writeValueAsString(spinnResp);
        
        PrintWriter out = resp.getWriter();

        // return
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();
    }
}

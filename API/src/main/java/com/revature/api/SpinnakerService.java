package com.revature.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Spinnaker.SpinnakerServiceObject;
import com.revature.model.Spinnaker.SpinnakerServiceResp;


@WebServlet(name = "Spinnaker", urlPatterns = { "/spinnaker" })
public class SpinnakerService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Make req Object
        SpinnakerServiceObject sSer = objectMapper.readValue(req.getInputStream(), SpinnakerServiceObject.class);
 
        // Find Resp Values
        String applicationCreated = createAppFunction(sSer.getGitUri(), sSer.getProjectName(), sSer.getBranch());
        String pipelineCreated = applicationCreated.equals("true") ? createPipeFunction(sSer.getGitUri(), sSer.getCloudProviders(), sSer.getEmail()) : "false";

        //Make resp Object
        SpinnakerServiceResp sResp = new SpinnakerServiceResp(applicationCreated, pipelineCreated);

        String result = objectMapper.writeValueAsString(sResp);
        PrintWriter out = resp.getWriter();

        //return (print out for debugging)
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();
    }

    // Obviously temporary Function follow
    private String createPipeFunction(String gitUri, String cloudProviders, String email) {
        return "true";
    }

    private String createAppFunction(String gitUri, String projectName, String branch) {
        return "true";
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Sorry! This servlet only takes POST commands. Sending request to POST...");
        doPost(req, resp);
    }
}

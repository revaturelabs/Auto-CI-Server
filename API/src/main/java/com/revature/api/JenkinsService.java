package com.revature.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.JenkinsServiceObject;
import com.revature.model.JenkinsServiceResp;

@WebServlet(name = "JenkinsService", urlPatterns = { "/jenkins" })
public class JenkinsService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Vars
        String jenkinsURL = req.getParameter("jenkinsURL");
        String githubURL = req.getParameter("githubURL");
        String projectName = req.getParameter("projectName");
        String slackChannel = req.getParameter("slackChannel");

        JenkinsServiceObject jSer = new JenkinsServiceObject(jenkinsURL, githubURL, projectName, slackChannel);

        String jobName = makeJenkinsJobFunction(jenkinsURL, githubURL);

        JenkinsServiceResp jResp = new JenkinsServiceResp(jobName);

        String result = objectMapper.writeValueAsString(jResp);
        PrintWriter out = resp.getWriter();

        //return 
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();
    }

    // temporary function, obviously
    private String makeJenkinsJobFunction(String jenkinsURL, String githubURL) {
        return "brandNewPipelineJob";
    }
}

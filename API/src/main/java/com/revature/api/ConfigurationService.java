package com.revature.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Configuration;
import com.revature.model.ConfigurationResp;

@WebServlet(name = "ConfigurationService", urlPatterns = {"/configuration" })
public class ConfigurationService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Jackson stuff
        ObjectMapper objectMapper = new ObjectMapper();

        // Vars and Build Configuration Object
        String githubUsername = req.getParameter("githubUsername");
        String jenkinsURI = req.getParameter("jenkinsURI");
        String projectName = req.getParameter("projectName");
        boolean generateGithubActions = req.getParameter("generateGithubActions").equals("true"); // Was Jenkins specified, or are we using Github Actions?
        boolean debug = req.getParameter("debug").equals("true");

        Configuration conf = new Configuration(githubUsername, jenkinsURI, projectName, generateGithubActions, debug);

        // Vars and Build Configuration Response
        String githubURL = "http://github.com/" + githubUsername + "/" + projectName + ".git";
        boolean madeHook = !generateGithubActions;

        ConfigurationResp confResp = new ConfigurationResp(githubURL, madeHook);

        String result = objectMapper.writeValueAsString(confResp);
        PrintWriter out = resp.getWriter();

        //return 
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();
    }
}

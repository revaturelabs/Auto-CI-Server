package com.revature.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.JenkinsServiceObject;

@WebServlet(name = "JenkinsService", urlPatterns = { "/jenkins" })
public class JenkinsService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Vars
        String repoURL = req.getParameter("repoURL");
        String jenkinsURI = req.getParameter("jenkinsURI");
        String projMetadata = req.getParameter("projMetadata");

        JenkinsServiceObject jSer = new JenkinsServiceObject(repoURL, jenkinsURI, projMetadata);

        //create entities
        // to do make model object to work with this endpoint

        String result = objectMapper.writeValueAsString(jSer);
        PrintWriter out = resp.getWriter();

        //return 
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();
    }
}

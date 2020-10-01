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
import com.revature.model.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "ConfigurationService", urlPatterns = {"/configuration" })
public class ConfigurationService extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final java.util.logging.Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Jackson stuff
        ObjectMapper objectMapper = new ObjectMapper();

        // Vars
        String gitUser = req.getParameter("gitUser");
        String jenkinsURI = req.getParameter("jenkinsURI");
        String jenOrGit = req.getParameter("jenOrGit"); // Was Jenkins specified, or are we using Github Actions?
        String projMetadata = req.getParameter("projMetadata");

        Configuration conf = new Configuration(gitUser, jenkinsURI, jenOrGit, projMetadata);

        //create entities
        // to do make model object to work with this endpoint

        String result = objectMapper.writeValueAsString(conf);
        PrintWriter out = resp.getWriter();

        //return 
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();

        log.info("Configuration Service Servlet successfully started");
    }
}

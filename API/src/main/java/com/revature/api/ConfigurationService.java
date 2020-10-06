package com.revature.api;

import java.io.IOException;
// import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.revature.model.Configuration.Configuration;
// import com.revature.model.Configuration.ConfigurationResp;

@WebServlet(name = "ConfigurationService", urlPatterns = {"/configuration" })
public class ConfigurationService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}

package com.revature.api;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.ProgressSingleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "Status", urlPatterns = { "/status" })
public class Status extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final Class<? extends Status> CLASS_NAME = this.getClass();
    private final Logger log = LoggerFactory.getLogger(CLASS_NAME);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();  

        ProgressSingleton progress = ProgressSingleton.instance();
        
        //return json back to request
        String result = objectMapper.writeValueAsString(progress);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // If someone sends a Get request here by mistake
        log.warn("Sorry! " + CLASS_NAME + " only takes POST requests. Sending data to POST...");
        super.doPost(req, resp);
    }
}

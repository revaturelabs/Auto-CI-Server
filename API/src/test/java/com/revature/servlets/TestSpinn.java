package com.revature.servlets;

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

@WebServlet(name = "TestSpinn", urlPatterns = { "/testspinn" })
public class TestSpinn extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Jackson stuff
        ObjectMapper objectMapper = new ObjectMapper();

        SpinnakerServiceObject init = objectMapper.readValue(req.getInputStream(), SpinnakerServiceObject.class);

        SpinnakerServiceResp response = new SpinnakerServiceResp("true", "true");

        String result = objectMapper.writeValueAsString(response);

        PrintWriter out = resp.getWriter();

        // return
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();
    }
}

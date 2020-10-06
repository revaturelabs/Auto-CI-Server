package com.revature.testServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Initialization.InitializationResp;

@WebServlet(name = "TestInit", urlPatterns = { "/testInit" })
public class TestInitService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Jackson stuff
        ObjectMapper objectMapper = new ObjectMapper();

        InitializationResp response = new InitializationResp("true");

        String result = objectMapper.writeValueAsString(response);

        PrintWriter out = resp.getWriter();

        // to demo time it takes to respond
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // return
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();
    }
}

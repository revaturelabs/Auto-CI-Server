package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Frontend.FrontendReq;


@WebServlet(name = "TestService", urlPatterns = { "/test" })
public class TestService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                // Jackson stuff
                ObjectMapper objectMapper = new ObjectMapper();        

                FrontendReq init = objectMapper.readValue(req.getInputStream(), FrontendReq.class);
            

                String result = objectMapper.writeValueAsString(init);

                PrintWriter out = resp.getWriter();

                //return 
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.setStatus(200);
                out.print(result);
                out.flush();
    }
}

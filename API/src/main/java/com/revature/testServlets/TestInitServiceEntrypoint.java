package com.revature.testServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.InitializationController;
import com.revature.model.Frontend.FrontendReq;
import com.revature.model.Initialization.InitializationResp;

@WebServlet(name = "TestInitEntrypoint", urlPatterns = { "/testInitEntrypoint" })
public class TestInitServiceEntrypoint extends HttpServlet {

    private static final long serialVersionUID = -2693027601970028768L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        InitializationController ic = new InitializationController();

        //testing Initialization Controller
        FrontendReq frontendObj = new FrontendReq();
        
        InitializationResp initResp = ic.runInitialization(frontendObj);

        System.out.println("\n\tHere's this:" + frontendObj + "\n");

        //sending results
        ObjectMapper objectMapper = new ObjectMapper();
        
        String result = objectMapper.writeValueAsString(initResp);
        
        PrintWriter out = resp.getWriter();

        // return
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if(result.contains("true")){
            resp.setStatus(200);
        } else {
            resp.setStatus(400);
        }
        out.print(result);
        out.flush();
    }
}
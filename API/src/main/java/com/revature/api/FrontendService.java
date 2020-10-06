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
import com.revature.model.Frontend.FrontendReq;

@WebServlet(name = "FrontendService", urlPatterns = { "/frontend" })
public class FrontendService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String result = "";

        // get data from frontend website convert to java model
        ObjectMapper objectMapper = new ObjectMapper();
        // Commenting out Unused Vars 202010061001-mwg
        FrontendReq frontendReqObj = objectMapper.readValue(req.getInputStream(), FrontendReq.class);
    
        //setup sington to allow us keep track of all service creations
        ProgressSingleton ps = ProgressSingleton.instance();

        //if this endpoint is called twice before finishing, respond with currenly working
        ps.setRunningStatus(true);
        ps.setFrontend("started");
        ps.setInitialization("not-done");
        ps.setConfiguration("not-done");
        ps.setJenkins("not-done");
        ps.setSpinnaker("not-done");

        result = objectMapper.writeValueAsString("started");

        //call start here
        ps.startInit(frontendReqObj);
        
        // if(!ps.getRunningStatus()){
            

        // } else {
        //     result = objectMapper.writeValueAsString("already running");
        // }

        //return with json here
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(200);
        out.print(result);
        out.flush();
    }
}

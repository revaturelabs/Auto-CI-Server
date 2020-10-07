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
import com.revature.controller.StartPipeline;
import com.revature.model.Frontend.FrontendReq;

@WebServlet(name = "FrontendService", urlPatterns = { "/frontend" })
public class FrontendService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Headers", "*");
        
        String result = "";

        // get data from frontend website convert to java model
        ObjectMapper objectMapper = new ObjectMapper();
        FrontendReq frontendReqObj = objectMapper.readValue(req.getInputStream(), FrontendReq.class);
    
        //setup sington to allow us keep track of all service creations
        StartPipeline pipeline = new StartPipeline();
        ProgressSingleton progress = ProgressSingleton.instance();
        
        //if this endpoint is called twice before finishing, respond with currenly working
        // if(!progress.getRunningStatus()){
        //     progress.setRunningStatus(true);
        //     pipeline.init(frontendReqObj);
        //     result = objectMapper.writeValueAsString("Pipeline has finished");
        // } else {
        //     result = objectMapper.writeValueAsString("already running, please try back soon");
        // }

        progress.setRunningStatus(true);
        pipeline.init(frontendReqObj);
        result = objectMapper.writeValueAsString("Pipeline has finished");

        //return with json here
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(200);
        out.print(result);
        out.flush();
    }
}

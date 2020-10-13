package com.revature.api;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.controller.ProgressSingleton;
import com.revature.controller.StartPipeline;
import com.revature.controller.ValidateData;
import com.revature.model.Frontend.FrontendObj;
import com.revature.model.Progress.ReturnResp;
import com.revature.model.Validate.ValidateObj;


@WebServlet(name = "FrontendService", urlPatterns = { "/frontend" })
public class FrontendService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        //respnse in JSON string
        String response = "";

        // get data from frontend website convert to java object model
        ObjectMapper objectMapper = new ObjectMapper();
        FrontendObj frontendReqObj = objectMapper.readValue(req.getInputStream(), FrontendObj.class);

        //printing input from request here
        printJson(frontendReqObj);

        //setup sington to allow us keep track of all service creations
        StartPipeline pipeline = new StartPipeline();
        ProgressSingleton progress = ProgressSingleton.instance();

        //checking validation
        ValidateObj validateResponse = ValidateData.validate(frontendReqObj);
        
        
        // if endpoint is called twice before finishing, respond with currenly working
        if(!progress.getRunningStatus()){

            //checking validation response here is OK start pipeline process and wait for response to be returned and set to validateResponse
            if(validateResponse.getIsValid()){
                progress.setRunningStatus(true);
                progress.setConfiguration("not started");
                progress.setInitialization("not started");
                progress.setJenkins("not started");
                progress.setSpinnaker("not started");
                ReturnResp pipeResp = pipeline.init(frontendReqObj);//maybe introduce new thread here
                validateResponse.setMessage(pipeResp.getMessage());
                response = objectMapper.writeValueAsString(validateResponse);
            } else {
                //if input not valid send back this message with error
                progress.setConfiguration("not started");
                progress.setInitialization("not started");
                progress.setJenkins("not started");
                progress.setSpinnaker("not started");
                response = objectMapper.writeValueAsString(validateResponse);
            }
            
        } else {
            //if pipeline process is already running send back this message
            validateResponse.setIsValid(false);
            validateResponse.setMessage("already running, please try back soon");
            response = objectMapper.writeValueAsString(validateResponse);
        }

        //return with json here
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(response);
        out.flush();
    }

    static <T> void printJson(T obj){
        // pretty print test
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String json;
        try {
            json = mapper.writeValueAsString(obj);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

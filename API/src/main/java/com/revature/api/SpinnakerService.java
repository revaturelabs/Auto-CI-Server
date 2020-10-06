package com.revature.api;

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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "Spinnaker", urlPatterns = { "/spinnaker" })
public class SpinnakerService extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final java.util.logging.Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Vars
        String gitUri = req.getParameter("gitUri");
        String cloudProviders = req.getParameter("cloudProviders");
        String email = req.getParameter("email");
        String projectName = req.getParameter("projectName");
        String branch = req.getParameter("branch");

        SpinnakerServiceObject sSer = new SpinnakerServiceObject(gitUri, cloudProviders, email, projectName, branch);
        
        boolean applicationCreated = createAppFunction(gitUri, projectName, branch);
        boolean pipelineCreated = applicationCreated && createPipeFunction(gitUri, cloudProviders, email);

        SpinnakerServiceResp sResp = new SpinnakerServiceResp(applicationCreated, pipelineCreated);

        String result = objectMapper.writeValueAsString(sResp);
        PrintWriter out = resp.getWriter();

        //return 
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();

        log.info("Spinnaker Service Servlet successfully started");
    }

    // Obvious temporary Function follow
    private boolean createPipeFunction(String gitUri, String cloudProviders, String email) {
        return true;
    }

    private boolean createAppFunction(String gitUri, String projectName, String branch) {
        return true;
    }
}

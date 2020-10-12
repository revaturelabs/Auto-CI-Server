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
import com.revature.model.Configuration.ConfigurationResp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "ConfigTest2", urlPatterns = { "/test-config" })
public class ConfigTest extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final Class<? extends ConfigTest> CLASS_NAME = this.getClass();
    private final Logger log = LoggerFactory.getLogger(CLASS_NAME);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Mock jenkins.  Receive the stuff that the controller sent.  If it's good, send back the response Alex gave.
        ObjectMapper objectMapper2 = new ObjectMapper(); //IT'S BREAKING HERE.  SOMETHING WITH VISIBLITY       

        ConfigurationResp configresp = new ConfigurationResp();
        configresp.setGithubURL("www.github.com");
        configresp.setMadeHook(true);

        String result = objectMapper2.writeValueAsString(configresp);

        PrintWriter out = resp.getWriter();

        // to demo time it takes to respond
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //return 
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

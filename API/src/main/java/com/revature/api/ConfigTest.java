package com.revature.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Configuration.ConfigurationResp;

@WebServlet(name = "ConfigTest2", urlPatterns = { "/configtest2" })
public class ConfigTest extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                // Mock jenkins.  Receive the stuff that the controller sent.  If it's good, send back the response Alex gave.
                
                System.out.println("YOU'RE IN THE SECOND SERVLET");
                ObjectMapper objectMapper = new ObjectMapper(); //IT'S BREAKING HERE.  SOMETHING WITH VISIBLITY       

                System.out.println("THAT SAME JSON SHOULD PRINT HERE: \n" + objectMapper.writeValueAsString(req));


                ConfigurationResp configresp = new ConfigurationResp();
                configresp.setGithubURL("successful github URL");
                configresp.setMadeHook("true");

                String result = objectMapper.writeValueAsString(configresp);

                PrintWriter out = resp.getWriter();

                //return 
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.setStatus(200);
                out.print(result);
                out.flush();




    }
}

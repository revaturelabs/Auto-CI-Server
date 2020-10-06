package com.revature.testServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Jenkins.JenkinsServiceResp;

@WebServlet(name = "JenkinsTest2", urlPatterns = { "/jenkinstest2" })
public class JenkinsTest extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           System.out.println("SECOND SERVLET IS RUNNING");
           ObjectMapper objectMapper2 = new ObjectMapper(); //IT'S BREAKING HERE.  SOMETHING WITH VISIBLITY       

           JenkinsServiceResp jenkinsresp = new JenkinsServiceResp();
           jenkinsresp.setDevJob("project-name/dev-pipe");
           jenkinsresp.setProdJob("project-name/prod-pipe");

           String result = objectMapper2.writeValueAsString(jenkinsresp);

           System.out.println("YOUR RESPONSE JSON:" + result);

           PrintWriter out = resp.getWriter();

           //return 
           resp.setContentType("application/json");
           resp.setCharacterEncoding("UTF-8");
           resp.setStatus(200);
           out.print(result);
           out.flush();

    }
}

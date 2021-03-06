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
import com.revature.model.Jenkins.JenkinsServiceResp;

@WebServlet(name = "JenkinsTest2", urlPatterns = { "/test-jenkins" })
public class JenkinsTest extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           ObjectMapper objectMapper2 = new ObjectMapper(); 
           JenkinsServiceResp jenkinsresp = new JenkinsServiceResp();
           jenkinsresp.setDevJob("project-name/dev-pipe");
           jenkinsresp.setProdJob("project-name/prod-pipe");

           String result = objectMapper2.writeValueAsString(jenkinsresp);

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
}

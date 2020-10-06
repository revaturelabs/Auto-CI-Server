package com.revature.testServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.controller.Jenkins;
import com.revature.model.Jenkins.JenkinsServiceObject;

@WebServlet(name = "JenkinsTest", urlPatterns = { "/jenkinstest" })
public class JenkinsTestEntrypoint extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            JenkinsServiceObject jenkins = new JenkinsServiceObject();
            jenkins.setGithubURL("www.github.com");
            jenkins.setJenkinsURL("www.jenkins.com");
            jenkins.setProjectName("test project");
            jenkins.setSlackChannel("slack channel");

            Jenkins controller = new Jenkins();

            controller.JenkinsService(jenkins);
        }
    }

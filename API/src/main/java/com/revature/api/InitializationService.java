package com.revature.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.NpmDependsObj;
import com.revature.model.Initialization;
import com.revature.model.MvnDataObj;
import com.revature.model.MvnDependsObj;
import com.revature.model.NpmDataObj;

@WebServlet(name = "InitializationService", urlPatterns = { "/initialization" })
public class InitializationService extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Vars
        String githubUsername = req.getParameter("githubUsername");
        String githubURL = req.getParameter("githubURL");
        String IDE = req.getParameter("IDE");
        boolean generateGithubActions = req.getParameter("generateGithubActions").equals("true");
        boolean isMaven = req.getParameter("depends").equals("true"); // Are we doing maven, or npm?

        // Vars in both maven and npm
        String projectName = req.getParameter("projectName");
        String version = req.getParameter("version");
        String description = req.getParameter("description");
        // NpmDependsObj dependencies = new NpmDependsObj(req.getParameter("dependencies"));

        Initialization initn;

        if (isMaven) {
            // Vars specific to maven
            String groupId = req.getParameter("groupId");
            String artifactId = req.getParameter("artifactId");
            String packaging = req.getParameter("packaging");
            String javaVersion = req.getParameter("javaVersion");
            String mainClass = req.getParameter("mainClass");
            MvnDependsObj dependencies = new MvnDependsObj(req.getParameter("dependencies"));

            MvnDataObj mavenData = new MvnDataObj(projectName, version, description, groupId, artifactId, packaging, javaVersion, mainClass, dependencies);

            initn = new Initialization(githubUsername, githubURL, IDE, generateGithubActions, isMaven, mavenData);
        } else {
            // Vars specific to npm
            String mainEntrypoint = req.getParameter("mainEntrypoint");
            String keywords = req.getParameter("keywords");
            String author = req.getParameter("author");
            String license = req.getParameter("license");
            NpmDependsObj dependencies = new NpmDependsObj(req.getParameter("dependencies"));
            NpmDependsObj devDependencies = dependencies;
            String scripts = req.getParameter("scripts");

            NpmDataObj npmData = new NpmDataObj(projectName, version, description, mainEntrypoint, keywords, author, license, dependencies, devDependencies, scripts);

            initn = new Initialization(githubUsername, githubURL, IDE, generateGithubActions, isMaven, npmData);
        }

        String result = objectMapper.writeValueAsString(initn);
        PrintWriter out = resp.getWriter();

        // return
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();
    }

    // Temporary Functions
    private String[] makeKeywordArray(String parameter) {
        String[] ret = { parameter };
        return ret;
    }

    private String[] makeScriptArray(String parameter) {
        String[] ret = { parameter };
        return ret;
    }
}

package com.revature.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.Initialization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "InitializationService", urlPatterns = { "/initialization" })
public class InitializationService extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final java.util.logging.Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Vars
        String gitUser = req.getParameter("gitUser");
        String repoURL = req.getParameter("repoURL");
        String mvnOrNpm = req.getParameter("mvnOrNpm"); // Are we doind maven, or bpm?
        String projMetadata = req.getParameter("projMetadata");
        String depends = req.getParameter("depends");

        Initialization initn = new Initialization(gitUser, repoURL, mvnOrNpm, projMetadata, depends);

        //create entities
        // to do make model object to work with this endpoint

        String result = objectMapper.writeValueAsString(initn);
        PrintWriter out = resp.getWriter();

        //return 
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(200);
        out.print(result);
        out.flush();

        log.info("Initialization Service Servlet successfully started");
    }
}

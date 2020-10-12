package com.revature.view;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "Index", urlPatterns = { "" })
public class Index extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final Class<? extends Index> CLASS_NAME = this.getClass();
    private final Logger log = LoggerFactory.getLogger(CLASS_NAME);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view = req.getRequestDispatcher("index.jsp");
        view.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // If someone sends a Get request here by mistake
        log.warn("Sorry! " + CLASS_NAME + " only takes GET requests. Sending data to POST...");
        super.doGet(req, resp);
    }
}
package com.revature.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.controller.SpinnakerController;

@WebServlet(name = "TestSpinnEntrypoint", urlPatterns = { "/testspinnentrypoint" })
public class TestSpinnEntrypoint extends HttpServlet{
    /**
     *
     */
    private static final long serialVersionUID = -2693027601970028768L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Starting TestSpinnEntrypoint");
        SpinnakerController sc = new SpinnakerController();

        //testing Spinnaker Controller
        System.out.println("Calling testSpinnaker");
        resp = sc.testSpinnaker(req, resp);
        System.out.println("Done testSpinnaker");
    }
}

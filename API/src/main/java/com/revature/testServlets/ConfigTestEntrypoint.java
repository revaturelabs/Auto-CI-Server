package com.revature.testServlets;

import java.io.IOException;
// import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.Config;
import com.revature.model.Configuration.Configuration;

@WebServlet(name = "ConfigTest", urlPatterns = { "/configtest" })
public class ConfigTestEntrypoint extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                // This is just the entrypoint, just a testing trigger.  Just feed a config to the controller.  The other servlet will be the mock jenkins server.
                ObjectMapper objectMapper = new ObjectMapper();        

                Configuration config = objectMapper.readValue(req.getInputStream(), Configuration.class);
                


                //return 
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.setStatus(200);
                Config controller = new Config();
                controller.ConfigService(config);

                //out.print(result);
                //out.flush();
    }
}

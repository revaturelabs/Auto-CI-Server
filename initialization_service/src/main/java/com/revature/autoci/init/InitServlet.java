package com.revature.autoci.init;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class InitServlet extends HttpServlet 
{
    private String token;
    @Override
    public void init() throws ServletException {
        super.init();
        // Load in github credentials
        token = System.getProperty("GITHUB_TOKEN", null);
    } 

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // Load in JSON message
        Gson gson = new Gson();
        JSONRequest data = gson.fromJson(req.getReader(), JSONRequest.class);
        boolean success = false;
        // create temp directory
        
        try
        {

            if(data.isMaven())
            {
                // Call Maven generator
                success = true;
            }
            else // Is node
            {
                // Call Node generator
                success = true;
            }

            // git 

        }
        
        if(success)
        {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        else
        {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        
    }
}

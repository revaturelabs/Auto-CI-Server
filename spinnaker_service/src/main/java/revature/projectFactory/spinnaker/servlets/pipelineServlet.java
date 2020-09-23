package revature.projectFactory.spinnaker.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import revature.projectFactory.spinnaker.POJO.PipelinePojo;
import revature.projectFactory.spinnaker.connectionUtils.ConnectionConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class pipelineServlet extends HttpServlet{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("The logger is working!");
        String body = "";
        while(req.getReader().ready()){
            body += req.getReader().readLine() + "\n";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        PipelinePojo obj = objectMapper.readValue(body, PipelinePojo.class);
        System.out.println(body); 
        System.out.println(obj.getJobName() + " " + obj.getGitUri() + " " + obj.getMetaData());
        System.out.println(ConnectionConstants.getSPINNAKERURI());
        resp.setStatus(200);
        resp.getWriter().println("success");
    }
}

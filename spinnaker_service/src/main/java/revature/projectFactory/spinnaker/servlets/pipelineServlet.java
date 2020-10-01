package revature.projectFactory.spinnaker.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import revature.projectFactory.spinnaker.POJO.PipelinePojo;
import revature.projectFactory.spinnaker.connectionUtils.ConnectionConstants;
import revature.projectFactory.spinnaker.spinnakerServices.ApplicationCreation;
import revature.projectFactory.spinnaker.spinnakerServices.IApplicationCreation;
import revature.projectFactory.spinnaker.spinnakerServices.IPipeLineCreation;
import revature.projectFactory.spinnaker.spinnakerServices.PipelineCreation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class pipelineServlet extends HttpServlet{
    /**
      *  Default serial id
	 */
    private static final long serialVersionUID = 1L;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private IApplicationCreation APPBUILDER;
    @Override
    public void init() throws ServletException {
        super.init();
        APPBUILDER = new ApplicationCreation();
    }

    @Override  
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Recieved Request for a new pipeline");
        String body = "";
        while(req.getReader().ready()){
            body += req.getReader().readLine() + "\n";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        PipelinePojo obj = objectMapper.readValue(body, PipelinePojo.class);
        if(APPBUILDER.create(obj.getProjectName(), obj.getEmail(), obj.getCloudProviders()) == 1){
            resp.getWriter().println("Application failed creating");
        }else{
            resp.getWriter().println("Application created");
        }
        IPipeLineCreation PIPELINEBUILDER = new PipelineCreation(ConnectionConstants.getSPINNAKERURI(), obj.getGitUri(), obj.getBranch());
        if(PIPELINEBUILDER.create()){
            resp.getWriter().println("Pipeline created");
        }else{
            resp.getWriter().println("Pipeline failed creating");
        }
        resp.setStatus(200);
    }
}

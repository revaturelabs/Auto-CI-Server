package revature.projectFactory.spinnaker.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import revature.projectFactory.spinnaker.POJO.PipelinePojo;
import revature.projectFactory.spinnaker.POJO.ReturnMessage;
import revature.projectFactory.spinnaker.connectionUtils.ConnectionConstants;
import revature.projectFactory.spinnaker.mapper.Mapper;
import revature.projectFactory.spinnaker.spinnakerServices.ApplicationCreation;
import revature.projectFactory.spinnaker.spinnakerServices.IApplicationCreation;
import revature.projectFactory.spinnaker.spinnakerServices.IPipeLineCreation;
import revature.projectFactory.spinnaker.spinnakerServices.PipelineCreation;
import revature.projectFactory.spinnaker.stringUtils.StringUtility;

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

    /**
     * Creates a Spinnakers application and pipeline
     * @author Reese Benson
     * @version 1.0.0
     * @since 10/9/2020
     */
    @Override  
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Spinnaker Service Recieved Request for a new pipeline");
        String body = "";
        resp.setStatus(200);
        while(req.getReader().ready()){
            body += req.getReader().readLine() + "\n";
        }
        Mapper mapper = new Mapper();
        PipelinePojo obj = mapper.pipelinePojoReadMapper(body);
        ReturnMessage result = new ReturnMessage();
        if(APPBUILDER.create(obj.getProjectName(), obj.getEmail(), StringUtility.asCommaSeperatedString(obj.getCloudProviders())) == 1){
            result.setApplicationCreated(false);;
            log.error("Application failed to create");
            resp.setStatus(500);
        }else{
            result.setApplicationCreated(true);
        }
        IPipeLineCreation PIPELINEBUILDER = new PipelineCreation(ConnectionConstants.getSPINNAKERURI(), obj.getGitUri(), obj.getBranch());
        if(PIPELINEBUILDER.create()){
            result.setPipelineCreated(true);
        }else{
            result.setPipelineCreated(false);
            log.error("Pipeline failed to create");
            resp.setStatus(500);
        }
        resp.getWriter().write(mapper.writeMapper(result));
    }
}

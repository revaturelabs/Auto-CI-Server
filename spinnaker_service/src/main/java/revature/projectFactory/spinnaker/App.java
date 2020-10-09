package revature.projectFactory.spinnaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import revature.projectFactory.spinnaker.connectionUtils.ConnectionConstants;
import revature.projectFactory.spinnaker.servlets.pipelineServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    final static Logger log = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        load();
        Tomcat server = new Tomcat();
        server.setPort(8080);
        server.setBaseDir(new File("target/tomcat").getAbsolutePath());
        server.getConnector();
        server.addWebapp("/api", new File("src/main/resources").getAbsolutePath());
        server.addServlet("/api", "pipelineServlet", new pipelineServlet()).addMapping("/pipeline");;
        try {
            server.start();
            server.getServer().await();
            log.info("Tomcat server created");
        } catch (LifecycleException e) {
            log.error("Server creation failed ", e);
            e.printStackTrace();
            System.out.println();
        }
    }

    /**
     * Loads properties from the app.properties file
     * @author Reese Benson
     */
    private static void load(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("app.properties"));
            ConnectionConstants.setSPINNAKERURI(properties.getProperty("SpinnakerURI", "localhost:8080"));
            System.out.println("properties loaded");
            log.info("Spinnaker properties loaded");
        } catch (IOException e1) {
            log.error("load spinnaker properties failed", e1);
            e1.printStackTrace();
        }
    }
}

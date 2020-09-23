package revature.projectFactory.spinnaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import revature.projectFactory.spinnaker.connectionUtils.ConnectionConstants;
import revature.projectFactory.spinnaker.servlets.pipelineServlet;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        load();
        Tomcat server = new Tomcat();
        server.setPort(8080);
        server.setBaseDir(new File("target/tomcat").getAbsolutePath());
        server.getConnector();
        server.addWebapp("/api", new File("src/main/static").getAbsolutePath());
        server.addServlet("/api", "pipelineServlet", new pipelineServlet()).addMapping("/pipeline");;
        try {
            server.start();
            server.getServer().await();
        } catch (LifecycleException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println();
        }
    }
    private static void load(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("app.properties"));
            ConnectionConstants.setSPINNAKERURI(properties.getProperty("SpinnakerURI", "localhost:8080"));
            System.out.println("properties loaded");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}

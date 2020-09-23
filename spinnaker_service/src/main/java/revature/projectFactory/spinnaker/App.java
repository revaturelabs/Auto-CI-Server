package revature.projectFactory.spinnaker;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import revature.projectFactory.spinnaker.servlets.pipelineServlet;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
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
}

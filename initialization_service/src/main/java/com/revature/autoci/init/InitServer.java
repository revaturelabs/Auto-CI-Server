package com.revature.autoci.init;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;


/**
 * Hello world!
 *
 */
public class InitServer
{
    public static void main( String[] args ) throws LifecycleException
    {
        int port = 8080;
        if(args.length > 0)
        {
            port = Integer.valueOf(args[0]);
        }
        final String base = new File("./").getAbsolutePath();
        Tomcat server = new Tomcat();
        server.setPort(Integer.valueOf(port));
        server.getConnector();
        server.addWebapp("", base);
        Wrapper wrapper = server.addServlet("", "Init", new InitServlet());
        wrapper.addMapping("/init/");
        wrapper.setLoadOnStartup(0);
        server.start();
    }
}

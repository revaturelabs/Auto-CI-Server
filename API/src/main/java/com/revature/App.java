package com.revature;
import java.io.File;
import java.util.Optional;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));
    private final static Logger log = LoggerFactory.getLogger("App.java");

    public static void main(String[] args) {

        final String base = new File("./").getAbsolutePath();
        Tomcat server = new Tomcat();
        server.setBaseDir(new File("target/tomcat/").getAbsolutePath());
        server.setPort(Integer.valueOf(port.orElse("8080")));
        server.getConnector();
        Context context = server.addWebapp("", base);
        context.setAltDDName(new File("src/main/WEB-INF/web.xml").getAbsolutePath());
        WebResourceRoot resources = new StandardRoot(context);
        WebResourceSet webResourceSet = new DirResourceSet(resources, "/WEB-INF/classes", base, "/");
        resources.addPreResources(webResourceSet);
        context.setResources(resources);
        try {
            server.start();
        } catch (LifecycleException e) {
            log.error("Failed starting server. " + e.getMessage());
            System.out.println(e);
        }
        server.getServer().await();
    }
}

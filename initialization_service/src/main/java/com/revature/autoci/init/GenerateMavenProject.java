package com.revature.autoci.init;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.*;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

public class GenerateMavenProject {

    public static void generateProject(String groupId, String artifactId, String version, String description, String name, String url, String packaging, String javaVersion, String mainClass, List<Map<String, String>> dependencies) {
        //initializing variables and objects
        String filename = "pom.xml";
        String filepath = "temp/";
        Model mvnFile = new Model();
        Writer writer;

        //setting project metaData
        mvnFile.setGroupId(groupId);
        mvnFile.setArtifactId(artifactId);
        mvnFile.setVersion(version);
        mvnFile.setDescription(description);
        mvnFile.setPackaging(packaging);
        mvnFile.setName(name);
        mvnFile.setUrl(url);

        //adding properties
        mvnFile.addProperty("exec.mainClass", mainClass);
        mvnFile.addProperty("maven.compiler.source", javaVersion);
        mvnFile.addProperty("maven.compiler.target", javaVersion);

        //adding dependencies
        List<Dependency> allDependencies = new ArrayList<>();
        Dependency newDependency;
        for (Map<String, String> dependencyMap: dependencies) {
            newDependency = new Dependency();
            newDependency.setGroupId(dependencyMap.get(groupId));
            newDependency.setArtifactId(dependencyMap.get(artifactId));
            if (dependencyMap.get("version") == null) {
                newDependency.setVersion("RELEASE");
            } else {
                newDependency.setVersion(dependencyMap.get("version"));
            }
            if (dependencyMap.get("scope") != null) {
                newDependency.setVersion(dependencyMap.get("scope"));
            }
            allDependencies.add(newDependency);
        }
        mvnFile.setDependencies(allDependencies);

        //adding build/plugins
        Build projectBuild = new Build();
        Plugin mvnAssemblyPlugin = new Plugin();
        mvnAssemblyPlugin.setArtifactId("maven-assembly-plugin");
        mvnAssemblyPlugin.setVersion("RELEASE");
        ConfigurationContainer pluginConfig = new ConfigurationContainer();
        mvnAssemblyPlugin.setConfiguration(pluginConfig);
        projectBuild.addPlugin(mvnAssemblyPlugin);
        mvnFile.setBuild(projectBuild);

        try {
            writer = new FileWriter(filepath + filename);
            new MavenXpp3Writer().write(writer, mvnFile);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
               
    }
    
}

package com.revature.autoci.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class GenerateMavenProject {

    public static void generateNewMavenProject(String groupId, String artifactId, String version, String description, String name, String url, String packaging, String javaVersion, String mainClass, List<Map<String, String>> dependencies, String IDE, String directoryToPush) {
        generateMvnFileStructure(groupId, directoryToPush);
        generateGitIgnoreFile(IDE, directoryToPush);
        generateMainJavaFile(mainClass, groupId, directoryToPush);
        generatePomFile(groupId, artifactId, version, description, name, url, packaging, javaVersion, mainClass,
                dependencies, directoryToPush);
    }

    private static void generateMvnFileStructure(String groupId, String directoryToPush) {
        // Parsing group id to determine folder structure in src
        String groupIdFolders = GetFoldersFromGroupId(groupId);

        // Adding src folder
        String srcPath = Paths.get(directoryToPush, "src/").toString();
        File srcDir = new File(srcPath);
        srcDir.mkdir();

        // Adding main/java/parsedGroupId directory
        String mainJavaPath = Paths.get(directoryToPush, "src/main/java/" , groupIdFolders).toString();
        File mainJavaDir = new File(mainJavaPath);
        mainJavaDir.mkdirs();

        // Adding test/java/parsedGroupId directory
        String testJavaPath = Paths.get(directoryToPush, "src/test/java/", groupIdFolders).toString();
        File testJavaDir = new File(testJavaPath);
        testJavaDir.mkdirs();
    }

    private static String GetFoldersFromGroupId(String groupId) {
        String[] groupIdParts = groupId.split("\\.");
        String groupIdFolders = "";
        for (String groupIdPart : groupIdParts) {
            groupIdFolders += groupIdPart + "/";
        }
        return groupIdFolders;
    }

    private static void generateGitIgnoreFile(String IDE, String directoryToPush) {
        // URL to generate .gitignore for Maven, Java, and Git
        String gitIgnoreIoUrl = "https://www.toptal.com/developers/gitignore/api/maven,java,git";
        if (IDE != null) {
            gitIgnoreIoUrl += "," + IDE;
        }

        // Creating and sending HTTP Request
        try {
            URL requestURL;
            requestURL = new URL(gitIgnoreIoUrl);
            String readLine = null;
            HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = connection.getResponseCode();
            String gitIgnorePath = Paths.get(directoryToPush,".gitignore").toString();
            File gitIgnoreFile = new File(gitIgnorePath);
            FileWriter writer = new FileWriter(gitIgnoreFile);

            // Writing the HTTP response to the .gitignore file
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((readLine = in.readLine()) != null) {
                    writer.write(readLine + "\n");
                }
                writer.close();
                in.close();
            } else {
                System.err.println("Problem connecting to/receiving response from gitignore.io");
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        } catch (Exception e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
             return;
        }
    }

    private static void generateMainJavaFile(String mainClass, String groupId, String directoryToPush) {
        // Parsing groupId to return folder structure: com.example returns com/return/
        String groupIdFolders = GetFoldersFromGroupId(groupId);

        // Verifying that mainClass doesn't have .java or .class appended to end
        mainClass = mainClass.split("\\.")[0];

        // Checking if the mainclass was given as a file within a folder i.e Application/App.java
        String mainClassParts[] = mainClass.split("/");
        String mainClassFolders = "";
        List<String> additionsToPackage = new ArrayList<String>();
        String mainClassName = mainClass;
        if (mainClassParts.length > 1) {
            for (int i = 0; i < mainClassParts.length; i++) {
                if (i < mainClassParts.length - 1) {
                    mainClassFolders += mainClassParts[i] + "/";
                    additionsToPackage.add(mainClassParts[i]);
                } else {
                    mainClassName = mainClassParts[i];
                }
            }
        }
        // Constructing the string to be put into the java file
        String packageName = groupId;
        for (String packageAddition: additionsToPackage) {
            packageName += "." + packageAddition;
        }
        String javaString = "package " + packageName + ";\n\n";
        javaString += "public class " + mainClassName + " {\n\n";
        javaString += "\tpublic static void main(String[] args) {\n\n\t}\n}";

        String javaFilePath = Paths.get(directoryToPush,"src/main/java/" ,groupIdFolders,mainClass + ".java").toString();

        // Creating directory to add main class if necessary
        if (!mainClassFolders.equals("")) {
            File javaFileDirectory = new File(Paths.get(directoryToPush,"src/main/java/",groupIdFolders,mainClassFolders).toString());
            javaFileDirectory.mkdir();
        }

        // Creating java file
        File javaFile = new File(javaFilePath);
        try {
            javaFile.createNewFile();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter(javaFile);
            writer.write(javaString);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void generatePomFile(String groupId, String artifactId, String version, String description,
            String name, String url, String packaging, String javaVersion, String mainClass,
            List<Map<String, String>> dependencies, String directoryToPush) {
        // initializing variables and objects
        String filename = "pom.xml";
        String filepath = directoryToPush;
        Model mvnFile = new Model();
        Writer writer;

        // Setting project metadata
        mvnFile.setGroupId(groupId);
        mvnFile.setArtifactId(artifactId);
        mvnFile.setVersion(version);
        mvnFile.setDescription(description);
        mvnFile.setPackaging(packaging);
        mvnFile.setName(name);
        mvnFile.setUrl(url);

        // Adding properties
        // Making sure mainClass doesn't have a .java or .class at the end
        mainClass = mainClass.split("\\.")[0];
        mvnFile.addProperty("exec.mainClass", mainClass);
        mvnFile.addProperty("maven.compiler.source", javaVersion);
        mvnFile.addProperty("maven.compiler.target", javaVersion);

        // Adding dependencies
        List<Dependency> allDependencies = new ArrayList<>();
        Dependency newDependency;
        for (Map<String, String> dependencyMap : dependencies) {
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

        // Adding build/plugins
        Build projectBuild = new Build();
        Plugin mvnAssemblyPlugin = new Plugin();
        mvnAssemblyPlugin.setArtifactId("maven-assembly-plugin");
        mvnAssemblyPlugin.setVersion("RELEASE");
        List<PluginExecution> executions = new ArrayList<PluginExecution>();
        PluginExecution execution = new PluginExecution();
        execution.setPhase("package");
        execution.addGoal("single");
        executions.add(execution);
        mvnAssemblyPlugin.setExecutions(executions);

        // Because the configuration for each plugin is dependent on the actual plugin,
        // it must be added from a Dom object
        Xpp3Dom configDom = null;
        try {
            configDom = Xpp3DomBuilder.build(new StringReader(getMvnAssemblyPluginConfig(packaging)));
        } catch (XmlPullParserException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        mvnAssemblyPlugin.setConfiguration(configDom);
        projectBuild.addPlugin(mvnAssemblyPlugin);
        mvnFile.setBuild(projectBuild);

        // Writing pom.xml to directory to be pushed
        try {
            writer = new FileWriter(Paths.get(filepath, filename).toString());
            new MavenXpp3Writer().write(writer, mvnFile);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }               
    }

        // configuration details for the maven-assembly-plugin
        private static String getMvnAssemblyPluginConfig(String packaging) {
            String MvnAssemblyPluginConfig = "<configuration>" + "<descriptorRefs>" + "<descriptorRef>" + packaging
                    + "-with-dependencies</descriptorRef>" + "</descriptorRefs>" + "<archive>" + "<manifest>"
                    + "<mainClass>${exec.mainClass}</mainClass>" + "</manifest>" + "</archive>"
                    + "<appendAssemblyId>true</appendAssemblyId>" + "</configuration>";
            return MvnAssemblyPluginConfig;
        }
    
}
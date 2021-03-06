package com.revature.autoci.init.generators;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Build;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides static methods for generating a new Maven project.
 */
public class GenerateMavenProject {
    
    private static final Logger log = LoggerFactory.getLogger(GenerateMavenProject.class);
    
    static final String MAVEN_MODEL_VERSION = "4.0.0";
    static final String ASSEMBLY_PLUGIN_VERSION = "3.3.0";
    static final String CHECKSTYLE_PLUGIN_VERSION = "3.1.1";
    static final String CHECKSTYLE_FILENAME = "rev_checks.xml";
    static final int TAB_SPACES = 4;

    /**
     * Generates a new Maven project in the designated directory. Creates standard
     * folder structure, generates a generic .gitignore file, generates a generic
     * main java file, and generates a basic pom.xml file
     * @param groupId The Maven group id
     * @param artifactId The Maven artifact id
     * @param version
     * @param description
     * @param name A human-readable name
     * @param url The homepage for this project, possibly an online Git repository.
     * @param packaging The type of packaging to use for this project e.g. jar, war
     * @param javaVersion The JDK version to use for this project.
     * @param mainClass The main entrypoint for this application
     * @param dependencies A list of dependencies for this project, matching 
     * the dependency format used by maven.
     * @param IDE
     * @param directoryToPush The working directory to generate this project in.
     * @throws GenerationException
     */

    public static void generateNewMavenProject(String groupId, String artifactId, String version, String packageName, String description,
            String name, String url, String packaging, String javaVersion, String mainClass,
            List<Map<String, String>> dependencies, String IDE, String directoryToPush) throws GenerationException {
        generateMvnFileStructure(packageName, directoryToPush);
        generateGitIgnoreFile(IDE, directoryToPush);
        addCheckstyleFile(directoryToPush);
        generateMainJavaFile(mainClass, packageName, directoryToPush);
        generatePomFile(groupId, artifactId, version, packageName, description, name, url, packaging, javaVersion, mainClass,
                dependencies, directoryToPush);
    }

    // Creates the standard Maven project file structure in the designated directory
    private static void generateMvnFileStructure(String packageName, String directoryToPush) {
        // Parsing group id to determine folder structure in src
        String packageFolders = GetFoldersFromPackageName(packageName);

        // Adding src folder
        String srcPath = Paths.get(directoryToPush, "src/").toString();
        File srcDir = new File(srcPath);
        srcDir.mkdir();

        // Adding main/java/parsedGroupId directory
        String mainJavaPath = Paths.get(directoryToPush, "src/main/java/", packageFolders).toString();
        File mainJavaDir = new File(mainJavaPath);
        mainJavaDir.mkdirs();

        // Adding test/java/parsedGroupId directory
        String testJavaPath = Paths.get(directoryToPush, "src/test/java/", packageFolders).toString();
        File testJavaDir = new File(testJavaPath);
        testJavaDir.mkdirs();
        log.info("Maven Project File Structure successfully generated");

    }

    // Parse the packageName and returns what folders to create to follow standard Maven
    // project folder structure com.example.project -> com/example/project
    private static String GetFoldersFromPackageName(String packageName) {
        String[] packageParts = packageName.split("\\.");
        String packageFolders = "";
        for (String packagePart : packageParts) {
            packageFolders += packagePart + "/";
        }
        return packageFolders;
    }

    // Generates a generic .gitignore file for the maven project by sending a get
    // request to gitignore.io. Includes common files to ignore for maven, java,
    // git, and the IDE supplied by the user. Before using, insure that IDE string
    // is compatible with gitignore.io
    private static void generateGitIgnoreFile(String IDE, String directoryToPush) {
        // URL to generate .gitignore for Maven, Java, and Git
        String gitIgnoreIoUrl = "https://www.toptal.com/developers/gitignore/api/maven,java,git";
        if (IDE != null) {
            gitIgnoreIoUrl += "," + IDE;
        }
        // Generating .gitignore from URL and saving .gitignore file to directory
        GenerateProjectUtils.generateGitIgnoreFromUrl(gitIgnoreIoUrl, directoryToPush);
        log.info("Git Ingore File succesfully created and updated");
    }

    private static void addCheckstyleFile(String directoryToPush) throws GenerationException {
        InputStream fileStream = GenerateMavenProject.class.getClassLoader().getResourceAsStream(CHECKSTYLE_FILENAME);
        try {
            Files.copy(fileStream, Paths.get(directoryToPush, CHECKSTYLE_FILENAME));

            log.info("Checkstyle File succesfully created and updated");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Copying file to new project failed", e);
          
            throw new GenerationException(String.format("Could not copy %s to new project", CHECKSTYLE_FILENAME));
        }

    }

    // Generates the main java file using the name (and possibly folder names)
    // supplied.
    // If something like: "appFiles/app" will create the appFiles folder and put
    // app.java in that folder
    private static void generateMainJavaFile(String mainClass, String packageName, String directoryToPush) {
        // Parsing groupId to return folder structure: com.example returns com/return/
        String packageFolders = GetFoldersFromPackageName(packageName);
        String tabSpaceString = StringUtils.repeat(' ', TAB_SPACES);

        // Verifying that mainClass doesn't have .java or .class appended to end
        mainClass = mainClass.split("\\.")[0];

        // Checking if the mainclass was given as a file within a folder i.e
        // Application/App.java
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
        String javaString = "package " + packageName + ";\n\n";
        javaString += "public class " + mainClassName + " {\n\n";
        javaString += "\tpublic static void main(String[] args) {\n\n\t}\n}";
        javaString = javaString.replaceAll("\t", tabSpaceString); // Revature standard checkstyle prefers spaces to tabs

        String javaFilePath = Paths.get(directoryToPush, "src/main/java/", packageFolders, mainClass + ".java")
                .toString();

        // Creating directory to add main class if necessary
        if (!mainClassFolders.equals("")) {
            File javaFileDirectory = new File(
                    Paths.get(directoryToPush, "src/main/java/", packageFolders, mainClassFolders).toString());
            javaFileDirectory.mkdir();
            log.info("Java directory successfully created");
        }

        // Creating java file
        File javaFile = new File(javaFilePath);
        try {
            javaFile.createNewFile();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // TODO: Ensure UTF-8 charset
        try {
            FileWriter writer = new FileWriter(javaFile);
            writer.write(javaString);
            writer.close();
            log.info("Java file information write and updated");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Generates pom.xml in the designated directory from supplied parameters using
    // the apache.maven Model and other associated objects. Writes the Model object
    // to the pom.xml file using the apache.maven MavenXpp3Writer
    private static void generatePomFile(String groupId, String artifactId, String version, String packageName, String description,
            String name, String url, String packaging, String javaVersion, String mainClass,
            List<Map<String, String>> dependencies, String directoryToPush) throws GenerationException {
        // initializing variables and objects
        String filename = "pom.xml";
        String filepath = directoryToPush;
        Model mvnFile = new Model();
        Writer writer;

        // --- Setting project metadata ---
        mvnFile.setModelVersion(MAVEN_MODEL_VERSION);
        mvnFile.setGroupId(groupId);
        mvnFile.setArtifactId(artifactId);
        mvnFile.setVersion(version);
        mvnFile.setDescription(description);
        mvnFile.setPackaging(packaging);
        mvnFile.setName(name);
        mvnFile.setUrl(url);

        // Making sure mainClass doesn't have a .java or .class at the end
        mainClass = mainClass.split("\\.")[0];
        // giving the main class its fully qualified name
        mainClass = mainClass.replaceAll("/", ".");
        mainClass = packageName + "." + mainClass;
        
        // --- Adding properties ---
        mvnFile.addProperty("exec.mainClass", mainClass);
        mvnFile.addProperty("maven.compiler.source", javaVersion);
        mvnFile.addProperty("maven.compiler.target", javaVersion);
        mvnFile.addProperty("project.build.sourceEncoding", "UTF-8");
        mvnFile.addProperty("project.reporting.outputEncoding", "UTF-8");

        // --- Adding dependencies ---
        List<Dependency> allDependencies = new ArrayList<>();
        Dependency newDependency;
        for (Map<String, String> dependencyMap : dependencies) {
            newDependency = new Dependency();
            newDependency.setGroupId(dependencyMap.get("groupId"));
            newDependency.setArtifactId(dependencyMap.get("artifactId"));
            if (dependencyMap.get("version") == null) {
                newDependency.setVersion("RELEASE");
            } else {
                newDependency.setVersion(dependencyMap.get("version"));
            }
            if (dependencyMap.get("scope") != null) {
                newDependency.setScope(dependencyMap.get("scope"));
            }
            allDependencies.add(newDependency);
        }
        mvnFile.setDependencies(allDependencies);

        // --- Adding build/plugins ---
        Build projectBuild = new Build();

        // Maven Assembly Plugin
        Plugin mvnAssemblyPlugin = new Plugin();
        mvnAssemblyPlugin.setGroupId("org.apache.maven.plugins");
        mvnAssemblyPlugin.setArtifactId("maven-assembly-plugin");
        mvnAssemblyPlugin.setVersion(ASSEMBLY_PLUGIN_VERSION);
        List<PluginExecution> executions = new ArrayList<PluginExecution>();
        PluginExecution execution = new PluginExecution();
        execution.setPhase("package");
        execution.addGoal("single");
        executions.add(execution);
        mvnAssemblyPlugin.setExecutions(executions);

        // Maven Checkstyle Plugin
        Plugin mvnCheckstylePlugin = new Plugin();
        mvnCheckstylePlugin.setGroupId("org.apache.maven.plugins");
        mvnCheckstylePlugin.setArtifactId("maven-checkstyle-plugin");
        mvnCheckstylePlugin.setVersion(CHECKSTYLE_PLUGIN_VERSION);

        executions = new ArrayList<PluginExecution>();
        execution = new PluginExecution();
        execution.setPhase("validate");
        execution.addGoal("check");
        // Set execution config
        Xpp3Dom execConfigDom = null;
        try {
            execConfigDom = createCheckstyleExecutionConfig(true);
        } catch (XmlPullParserException | IOException e) {
            throw new GenerationException("Failed to generate config DOM");
        }
        execution.setConfiguration(execConfigDom);
        executions.add(execution);
        mvnCheckstylePlugin.setExecutions(executions);

        // Because the configuration for each plugin is dependent on the actual plugin,
        // it must be added from a Dom object
        Xpp3Dom configDomAssembly = null;
        Xpp3Dom configDomCheckstyle = null;
        try {
            configDomAssembly = createMvnAssemblyPluginConfig();
            configDomCheckstyle = createMvnCheckstylePluginConfig(CHECKSTYLE_FILENAME);
        } catch (XmlPullParserException | IOException e) {
            throw new GenerationException("Failed to generate config DOM");
        }
        mvnAssemblyPlugin.setConfiguration(configDomAssembly);
        mvnCheckstylePlugin.setConfiguration(configDomCheckstyle);

        projectBuild.addPlugin(mvnAssemblyPlugin);
        projectBuild.addPlugin(mvnCheckstylePlugin);
        mvnFile.setBuild(projectBuild);

        // Writing pom.xml to directory to be pushed
        try {
            writer = new FileWriter(Paths.get(filepath, filename).toString());
            new MavenXpp3Writer().write(writer, mvnFile);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new GenerationException("Failed to create pom.xml file");
        }
    }

    // Returns configuration details for the maven-assembly-plugin,
    // which we'll want to package the project with dependencies for app deployment
    private static Xpp3Dom createMvnAssemblyPluginConfig() throws XmlPullParserException, IOException {
        String mvnAssemblyPluginConfig = "<configuration>" + "<descriptorRefs>" + "<descriptorRef>" 
                + "jar-with-dependencies</descriptorRef>" + "</descriptorRefs>" + "<archive>" + "<manifest>"
                + "<mainClass>${exec.mainClass}</mainClass>" + "</manifest>" + "</archive>"
                + "<appendAssemblyId>true</appendAssemblyId>" + "</configuration>";

        return Xpp3DomBuilder.build(new StringReader(mvnAssemblyPluginConfig));

    }

    // Returns configuration details for the maven-checkstyle-plugin
    private static Xpp3Dom createMvnCheckstylePluginConfig(String checkstyleFilename)
            throws XmlPullParserException, IOException {
        String pluginConfig = String.format("<configuration> <configLocation> %s </configLocation> </configuration>",
                checkstyleFilename);
        return Xpp3DomBuilder.build(new StringReader(pluginConfig));
    }

    // Returns configuration details for the maven-checkstyle-plugin validate
    // execution.
    private static Xpp3Dom createCheckstyleExecutionConfig(boolean failOnViolation)
            throws XmlPullParserException, IOException {
        String config = String.format("<configuration> <failOnViolation> %b </failOnViolation> </configuration>",
                failOnViolation);
        return Xpp3DomBuilder.build(new StringReader(config));
    }

}

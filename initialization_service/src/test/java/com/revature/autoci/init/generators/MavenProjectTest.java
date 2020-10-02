package com.revature.autoci.init.generators;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.ElementSelectors;
import org.xmlunit.matchers.CompareMatcher;

public class MavenProjectTest {

    Path tempPath;
    String IDE = "vscode";
    String mainClass = "AppFolder/App";
    String groupId = "com.mycompany";
    String mainClassPath = "src/main/java/com/mycompany/AppFolder/";
    String mainClassName = "App";

    @Before
    public void setup() throws IOException, GenerationException {
        tempPath = Files.createTempDirectory("mvnt");
        tempPath.toFile().deleteOnExit();
        List<Map<String, String>> dependencies = new ArrayList<Map<String, String>>();
        Map<String, String> junitDep = new HashMap<String, String>();
        junitDep.put("groupId", "junit");
        junitDep.put("artifactId", "junit");
        junitDep.put("version", "4.12");
        junitDep.put("scope", "test");
        Map<String, String> mvnProjectDep = new HashMap<String, String>();
        mvnProjectDep.put("groupId", "org.apache.maven");
        mvnProjectDep.put("artifactId", "maven-project");
        dependencies.add(junitDep);
        dependencies.add(mvnProjectDep);

        GenerateMavenProject.generateNewMavenProject(groupId, "test-app", "1.0.0", "description", "test-app",
                "mycompany.com", "jar", "1.8", mainClass, dependencies, IDE, tempPath.toString());
    }

    @Test
    public void createsFileStructure() {
        // splitting groupId to get folder structure
        String[] splitGroupId = groupId.split("\\.");
        String groupIdFolders = "";
        for (String folder : splitGroupId) {
            groupIdFolders += folder + "/";

        }

        Path mainClassPath = Paths.get(tempPath.toString(), "src/", "main/", "java/", groupIdFolders,
                mainClass + ".java");
        assertTrue(Files.exists(mainClassPath));
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "src/", "test/", "java/", groupIdFolders)));
        assertTrue(Files.exists(Paths.get(tempPath.toString(), ".gitignore")));
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "pom.xml")));
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "rev_checks.xml")));
    }

    @Test
    public void correctGitIgnore() throws FileNotFoundException {
        Path gitIgnorePath = Paths.get(tempPath.toString(), ".gitignore");
        Scanner reader = new Scanner(gitIgnorePath.toFile());
        boolean hasJava = false;
        boolean hasMaven = false;
        boolean hasGit = false;
        boolean hasIDE = false;
        String line;
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            if (line.toLowerCase().contains("java")) {
                hasJava = true;
            }
            if (line.toLowerCase().contains("maven")) {
                hasMaven = true;
            }
            if (line.toLowerCase().contains("git")) {
                hasGit = true;
            }
            if (line.toLowerCase().contains(IDE.toLowerCase())) {
                hasIDE = true;
            }
        }
        reader.close();
        assertTrue(hasJava);
        assertTrue(hasMaven);
        assertTrue(hasGit);
        assertTrue(hasIDE);
    }

    @Test
    public void correctMainJavaFile() throws FileNotFoundException {
        Scanner expectedReader = new Scanner(
                MavenProjectTest.class.getClassLoader().getResourceAsStream("test_mainClass.txt"));
        Path fullMainClassPath = Paths.get(tempPath.toString(), mainClassPath, mainClassName + ".java");
        Scanner actualReader = new Scanner(fullMainClassPath.toFile());
        Boolean sameJavaFile = true;
        String expectedLine;
        String actualLine = "";
        while (expectedReader.hasNextLine()) {
            expectedLine = expectedReader.nextLine();
            if (actualReader.hasNextLine()) {
                actualLine = actualReader.nextLine();
            }
            sameJavaFile = (expectedLine.equals(actualLine));
        }
        assertTrue(sameJavaFile);
    }

    @Test
    public void correctPomXml() throws FileNotFoundException {
        InputStream expectedStream = MavenProjectTest.class.getClassLoader().getResourceAsStream("test_pom.xml");
        InputStream actualStream = new FileInputStream(Paths.get(tempPath.toString(), "pom.xml").toFile());


        assertThat(Input.fromStream(expectedStream), CompareMatcher.isSimilarTo(Input.fromStream(actualStream)).withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName)).ignoreWhitespace());
    }
    
}

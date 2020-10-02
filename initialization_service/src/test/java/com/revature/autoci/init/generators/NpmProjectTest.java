package com.revature.autoci.init.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.junit.Before;
import org.junit.Test;

public class NpmProjectTest {

    Path tempPath;
    String ESLINT_CONFIG_FILENAME = GenerateNpmProject.ESLINT_CONFIG_FILENAME;
    String IDE = "vscode";

    @Before
    public void setup() throws IOException, GenerationException {
        tempPath = Files.createTempDirectory("npmt");
        tempPath.toFile().deleteOnExit();
        Map<String, String> scripts = new HashMap<String, String>();
        scripts.put("test", "echo \"This is a test\" && exit 1");
        List<String> keywords = new ArrayList<String>();
        keywords.add("test");
        Map<String, String> dependencies = new HashMap<String, String>();
        dependencies.put("testDepend", "1.0.0");
        Map<String, String> devDependencies = new HashMap<String, String>();
        devDependencies.put("testDevDepend", "1.0.0");
        String directoryToPush = tempPath.toString();

        GenerateNpmProject.generateNewNpmProject("testproject", "author", "1.0.0", "description", "index.js",
                "https://github.com/author/repo.git", "MIT", scripts, keywords, dependencies, devDependencies, IDE,
                directoryToPush);
    }

    @Test
    public void createsProjectStructure() {
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "bin/")));
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "lib/")));
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "doc/")));
        assertTrue(Files.exists(Paths.get(tempPath.toString(), ".gitignore")));
        assertTrue(Files.exists(Paths.get(tempPath.toString(), ESLINT_CONFIG_FILENAME)));
        assertTrue(Files.exists(Paths.get(tempPath.toString(), "package.json")));
    }

    @Test
    public void correctGitIgnore() throws FileNotFoundException {
        Path gitIgnorePath = Paths.get(tempPath.toString(), ".gitignore");
        Scanner reader = new Scanner(gitIgnorePath.toFile());
        boolean hasNode = false;
        boolean hasGit = false;
        boolean hasIDE = false;
        String line;
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            if (line.toLowerCase().contains("node")) {
                hasNode = true;
            }
            if (line.toLowerCase().contains("git")) {
                hasGit = true;
            }
            if (line.toLowerCase().contains(IDE.toLowerCase())) {
                hasIDE = true;
            }
        }
        reader.close();
        assertTrue(hasNode);
        assertTrue(hasGit);
        assertTrue(hasIDE);
    }

    @Test
    public void correctESLintFile() throws FileNotFoundException {
        InputStreamReader expectedReader = new InputStreamReader(NpmProjectTest.class.getClassLoader().getResourceAsStream(ESLINT_CONFIG_FILENAME));       
        JsonReader expectedJsonReader = new JsonReader(expectedReader);
        JsonElement expectedJson = JsonParser.parseReader(expectedJsonReader);

        File esLintFile = Paths.get(tempPath.toString(), ESLINT_CONFIG_FILENAME).toFile();
        InputStreamReader actualReader = new InputStreamReader(new FileInputStream(esLintFile));
        JsonReader actualJsonReader = new JsonReader(actualReader);                  
        JsonElement actualJson = JsonParser.parseReader(actualJsonReader);

        assertEquals(expectedJson, actualJson);    
    }

    @Test
    public void correctPackageJson() throws FileNotFoundException {
        InputStreamReader expectedReader = new InputStreamReader(NpmProjectTest.class.getClassLoader().getResourceAsStream("test_package.json"));
        JsonReader expectJsonReader = new JsonReader(expectedReader);
        JsonElement expectedJson = JsonParser.parseReader(expectJsonReader);

        File packageJsonFile = Paths.get(tempPath.toString(), "package.json").toFile();
        InputStreamReader actualReader = new InputStreamReader(new FileInputStream(packageJsonFile));
        JsonReader actualJsonReader = new JsonReader(actualReader);
        JsonElement actualJson = JsonParser.parseReader(actualJsonReader);

        assertEquals(expectedJson, actualJson);
    }
    
}

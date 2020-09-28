package com.revature.autoci.init;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

public class GenerateNpmProject {
    static final String ESLINT_VERSION = "^7.10.0";
    static final String ESLINT_CONFIG_FILENAME = ".eslintrc.json";
    // Generates a new npm project in the designated directory. Creates standard folder structure, generates 
    // a generic .gitignore file, and generates a basic package.json file
    public static void generateNewNpmProject(String projectName, String author, String version, String description,
            String mainEntrypoint, String gitUrl, String license, Map<String, String> scripts, List<String> keywords,
            Map<String, String> dependencies, Map<String, String> devDependencies, String IDE, String directoryToPush)
            throws GenerationException {
        generateNpmFileStructure(directoryToPush);
        generateGitIgnoreFile(IDE, directoryToPush);
        addESLintConfig(directoryToPush);
        generatePackageJSON(projectName, version, description, mainEntrypoint, gitUrl, scripts, keywords, author, license, dependencies, devDependencies, directoryToPush);
    }

    // Creates a common npm project file structure in the designated directory, adding bin, lib, and doc folders
    private static void generateNpmFileStructure(String directoryToPush) {
        File baseFolder = Paths.get(directoryToPush).toFile();
        // Adding bin folder
        File binDir = new File(baseFolder, "bin/");
        binDir.mkdir();

        // Adding lib folder
        File libDir = new File(baseFolder, "lib/");
        libDir.mkdir();

        // Adding doc folder
        File docDir = new File(baseFolder, "doc/");
        docDir.mkdir();
    }

    // Generates a generic .gitignore file for the npm project by sending a get request to gitignore.io.
    // Includes common files to ignore for node and the IDE supplied by the user.
    // Before using, insure that IDE string is compatible with gitignore.io
    private static void generateGitIgnoreFile(String IDE, String directoryToPush) {
        // URL to generate .gitignore for NodeJS
        String gitIgnoreIoUrl = "https://www.toptal.com/developers/gitignore/api/node";
        if (IDE != null) {
            gitIgnoreIoUrl += "," + IDE;
        }
    
        // Generating .gitignore from URL and saving .gitignore file to directory
        GenerateProjectUtils.generateGitIgnoreFromUrl(gitIgnoreIoUrl, directoryToPush);
    }

    private static void addESLintConfig(String directoryToPush) throws GenerationException
    {
        InputStream fileStream = GenerateNpmProject.class.getClassLoader().getResourceAsStream(ESLINT_CONFIG_FILENAME);
        try {
            Files.copy(fileStream, Paths.get(directoryToPush, ESLINT_CONFIG_FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
            throw new GenerationException(String.format("Could not copy %s to new project", ESLINT_CONFIG_FILENAME));
        }
    }

    // Generates the package.json file in designated directory using the supplied parameters for the npm project.
    // Creates a NpmProject obect from the parameters, then writes that object to package.json using Gson
    private static void generatePackageJSON(String projectName, String version, String description, String mainEntrypoint, String gitUrl, Map<String, String> scripts, List<String> keywords, String author, String license, Map<String, String> dependencies, Map<String, String> devDependencies, String directoryToPush) {

        Map<String, String> repository = new HashMap<String, String>();
        repository.put("type", "git");
        repository.put("url", gitUrl);
        Map<String, String> finalDevDependencies = new HashMap<>(devDependencies);
        finalDevDependencies.putIfAbsent("eslint", ESLINT_VERSION);

        // initializing project object from paramaters
        NpmProject project = new NpmProject(projectName, description, version, mainEntrypoint, scripts, repository, keywords, author, license, dependencies, devDependencies);

        // converting the project object to JSON and writing it to package.json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File packageJSON = new File(directoryToPush, "package.json");
        try {
            FileWriter writer = new FileWriter(packageJSON);
            gson.toJson(project, writer);
            writer.close();
        } catch (JsonIOException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

                
    }

}
package com.revature.autoci.init;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

public class GenerateNpmProject {

    public static void generateNewNpmProject(String projectName, String author, String version, String description,
            String mainEntrypoint, String gitUrl, String license, Map<String, String> scripts, List<String> keywords,
            Map<String, String> dependencies, Map<String, String> devDependencies, String IDE, String directoryToPush) {
        generateNpmFileStructure(directoryToPush);
        generateGitIgnoreFile(IDE, directoryToPush);
        generatePackageJSON(projectName, version, description, mainEntrypoint, gitUrl, scripts, keywords, author, license, dependencies, devDependencies, directoryToPush);
    }

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

    private static void generateGitIgnoreFile(String IDE, String directoryToPush) {
        // URL to generate .gitignore for Maven, Java, and Git
        String gitIgnoreIoUrl = "https://www.toptal.com/developers/gitignore/api/node";
        if (IDE != null) {
            gitIgnoreIoUrl += "," + IDE;
        }

        GenerateProjectUtils.generateGitIgnoreFromUrl(gitIgnoreIoUrl, directoryToPush);
    }

    private static void generatePackageJSON(String projectName, String version, String description, String mainEntrypoint, String gitUrl, Map<String, String> scripts, List<String> keywords, String author, String license, Map<String, String> dependencies, Map<String, String> devDependencies, String directoryToPush) {

        Map<String, String> repository = new HashMap<String, String>();
        repository.put("type", "git");
        repository.put("url", gitUrl);
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
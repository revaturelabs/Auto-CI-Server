package com.revature.autoci.init;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.revature.autoci.init.generators.GenerateGithubActions;
import com.revature.autoci.init.generators.GenerateJenkinsfile;
import com.revature.autoci.init.generators.GenerateMavenProject;
import com.revature.autoci.init.generators.GenerateNpmProject;
import com.revature.autoci.init.generators.GenerateSpinnaker;
import com.revature.autoci.init.generators.GenerationException;
import com.revature.autoci.init.generators.HelmGenerate;
import com.revature.autoci.init.generators.LocalGitRepo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitServlet extends HttpServlet {
    static final String SECRET_DIR = "secrets/";
    private String token;
    private Gson gson;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        // Load in github credentials
        if((token = System.getProperty("GITHUB_TOKEN")) == null)
        {
            try
            {
                
                token = new String(Files.readAllBytes(Paths.get(SECRET_DIR, "github-token")), StandardCharsets.UTF_8).trim();
                log.info("Github credentials succesfully received");
            }
            catch(IOException e)
            {
                log.error("Failed to find github token in environment or secrets", e);;
                throw new ServletException("Failed to find github token in environment or secrets/");
            }
        }
        // Setup custom JSON deseriailizers
        Gson temp = new Gson();
        GsonBuilder builder = new GsonBuilder();

        // Npm json deserializer
        JsonDeserializer<NpmJSON> deserializer = new JsonDeserializer<NpmJSON>(){
            @Override
            public NpmJSON deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                JsonObject jsonObj = json.getAsJsonObject();
                Type listmap = new TypeToken<List<Map<String, String>>> (){}.getType();
                Type listType = new TypeToken<List<String>> (){}.getType();
                Type mapType = new TypeToken<Map<String,String>> (){}.getType();

                List<Map<String, String>> depList = temp.fromJson(jsonObj.get("dependencies"), listmap);

                List<Map<String, String>> devDepList = temp.fromJson(jsonObj.get("devDependencies"), listmap);
                List<String> keywordList = temp.fromJson(jsonObj.get("keywords"), listType);
                List<Map<String, String>> scriptList = temp.fromJson(jsonObj.get("scripts"), listmap);

                Map<String, String> dependencies = new HashMap<>();
                for(Map<String, String> dep: depList)
                {
                    dependencies.put(dep.get("name"), dep.get("version"));
                }

                Map<String, String> devDependencies = new HashMap<>();
                for(Map<String, String> dep: devDepList)
                {
                    devDependencies.put(dep.get("name"), dep.get("version"));
                }

                Map<String, String> scripts = new HashMap<>();
                for(Map<String, String> dep: scriptList)
                {
                    scripts.put(dep.get("command"), dep.get("script"));
                }

                return new NpmJSON(jsonObj.get("projectName").getAsString(), jsonObj.get("description").getAsString(), 
                jsonObj.get("version").getAsString(), jsonObj.get("mainEntrypoint").getAsString(),
                jsonObj.get("author").getAsString(), jsonObj.get("license").getAsString(), 
                keywordList, dependencies, devDependencies, scripts);
            }
        };

        builder.registerTypeAdapter(NpmJSON.class, deserializer);
        gson = builder.create();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load in JSON message
        JSONRequest data = gson.fromJson(req.getReader(), JSONRequest.class);

        boolean success = false;
        // create temp directory
        Path tempPath = Files.createTempDirectory("init");
        System.out.println(tempPath.toAbsolutePath().toString());
        try (LocalGitRepo git = new LocalGitRepo(data.getGithubURL(), tempPath, token)) {
            String projectName = null;
            String appVersion = null;
            String imageName = null;
            if (data.isMaven()) {
                MavenJSON mavenData = data.getMavenData();
                projectName = mavenData.getProjectName();
                appVersion = mavenData.getVersion();
                System.out.println("Generating maven project");
                
                GenerateMavenProject.generateNewMavenProject(mavenData.getGroupId(),
                    mavenData.getArtifactId(), mavenData.getVersion(), mavenData.getDescription(),
                    mavenData.getDescription(), data.getGithubURL(), mavenData.getPackaging(),
                    mavenData.getJavaVersion(), mavenData.getMainClass(),
                    mavenData.getDependencies(), data.getIDE(), tempPath.toString());
                log.info("Maven Project with all required files and configuration succesfully generated");
            } else // Is node
            {
                NpmJSON npmData = data.getNpmData();
                projectName = npmData.getProjectName();
                appVersion = npmData.getVersion();
                System.out.println("Generating Node project");

                GenerateNpmProject.generateNewNpmProject(npmData.getProjectName(), npmData.getAuthor(), npmData.getVersion(), 
                    npmData.getDescription(), npmData.getMainEntrypoint(), data.getGithubURL(),
                    npmData.getLicense(), npmData.getScripts(), npmData.getKeywords(),
                    npmData.getDependencies(), npmData.getDevDependencies(), data.getIDE(), tempPath.toString());
                log.info("Node JS Project with all required files and configuration succesfully generated");
            }
            imageName = projectName;
            // Generate Helm Chart
            HelmGenerate.generateHelmChart(projectName.toLowerCase().trim().replace(' ', '-'), appVersion, projectName, tempPath.toString(), false);
            log.info("HELM chart with configuration and files successfully generated");

            // Generate Spinnaker pipeline JSON file
            GenerateSpinnaker.generateSpinnaker(projectName, tempPath.toString());
            log.info("Pipeline JSON file with configuration successfully generated");

            // Generate jenkinsfile in top-level directory
            GenerateJenkinsfile.generateJenkinsfile(data.isMaven(), tempPath.toString());
                log.info("Jenkinsfile successfully generated");

            try 
            {
                if(data.shouldGenerateGHActions())
                {
                    GenerateGithubActions.GenerateFile(tempPath.toString());
                }
                
                // git branch, add, commit, push
                git.addAndCommitAll();
                git.branchDevAndProd();
                git.pushToRemote();
                success = true;
                log.info("Github adding, commiting and push actions are successful");
            } 
            catch (TimeoutException e) 
            {
                success = false;
                log.error("Github commands failed due to timeout ", e);
                e.printStackTrace();
            }
        }
        catch(GenerationException e)
        {
            success = false;
            log.error("Github command generation failed", e);
            e.printStackTrace();
        }
        finally
        {
            // delete temp directory
            if(!SystemUtils.IS_OS_WINDOWS)
            {
                FileUtils.deleteDirectory(tempPath.toFile());
            }
        }
        
        if(success)
        {
            resp.setStatus(HttpServletResponse.SC_OK);
            log.info("Servelet functioned as desired");
            return;
        }
        else
        {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Servelet did not function as desired");
            return;
        }
        
    }
}

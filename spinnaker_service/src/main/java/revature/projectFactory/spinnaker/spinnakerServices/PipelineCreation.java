package revature.projectFactory.spinnaker.spinnakerServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PipelineCreation implements IPipeLineCreation {
    private final String pipelineCreateEndpoint;
    private String gitHubUri;
    private final String branch;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private void setupGitUri(){
        gitHubUri = gitHubUri.replaceFirst("github", "raw.githubusercontent");
        gitHubUri +="/"+branch+"/Spinnaker.json";
    }

    private String getJSONFile(){
        setupGitUri();
        StringBuffer responseBody = new StringBuffer();
        try{
            URL url = new URL(gitHubUri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            String append;
            while((append = reader.readLine()) != null){
                responseBody.append(append + "\n");
            }
            log.info("making request to " +gitHubUri + " Response: "+ connection.getResponseCode() + "\n" + responseBody);
            reader.close();
            connection.disconnect();
        }catch(IOException e){
            e.printStackTrace();
        }
        return responseBody.toString();
    }
    
    @Override
    public boolean create() {
        boolean status = false;
        String jsonInputString = getJSONFile();
        try{
            URL url = new URL(pipelineCreateEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }
            StringBuffer responseBody = new StringBuffer();
            BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            while(reader.ready()){
                responseBody.append(reader.readLine() + "\n");
            }
            status = connection.getResponseCode() > 199 && connection.getResponseCode()<300;
            log.info("PipeLine creation response: " + connection.getResponseCode() + "\n" + responseBody);
            reader.close();
            connection.disconnect();
        }catch(IOException e){
            e.printStackTrace();
        }
        return status;
    }

    public PipelineCreation(String pipelineCreateEndpoint, String gitHubUri, String branch) {
        this.pipelineCreateEndpoint = pipelineCreateEndpoint + "/pipelines";
        this.gitHubUri = gitHubUri;
        this.branch = branch;
    }
    
}
package revature.projectFactory.spinnaker.spinnakerServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import revature.projectFactory.spinnaker.POJO.PipelineCreationPojo;

public class PipelineCreation implements IPipeLineCreation {
    private final String pipelineCreateEndpoint;
    private String gitHubUri;
    private final String branch;
    private void setupGitUri(){
        gitHubUri.replaceFirst("github", "raw.githubusercontent");
        gitHubUri +="/"+branch+"/Spinnaker.json";
    }

    private String getJSONFile(){
        setupGitUri();
        StringBuilder responseBody = new StringBuilder();
        try{
            URL url = new URL(gitHubUri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            while(reader.ready()){
                responseBody.append(reader.readLine() + "\n");
            }
            System.out.println("Response:" + connection.getResponseCode() + "\n" + responseBody);
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return responseBody.toString();
    }
    
    @Override
    public boolean create(PipelineCreationPojo pipline) {
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
            StringBuilder responseBody = new StringBuilder();
            BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            while(reader.ready()){
                responseBody.append(reader.readLine() + "\n");
            }
            System.out.println("Response:" + connection.getResponseCode() + "\n" + responseBody);
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public PipelineCreation(String pipelineCreateEndpoint, String gitHubUri, String branch) {
        this.pipelineCreateEndpoint = pipelineCreateEndpoint;
        this.gitHubUri = gitHubUri;
        this.branch = branch;
    }
    
}

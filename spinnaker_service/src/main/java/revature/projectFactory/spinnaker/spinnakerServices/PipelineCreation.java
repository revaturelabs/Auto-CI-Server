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

    PipelineCreation(String spinnakerURI){
        pipelineCreateEndpoint = spinnakerURI+"/pipelines";
    }
    
    @Override
    public boolean create(PipelineCreationPojo pipline) {
        try{

            URL url = new URL(pipelineCreateEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            String jsonInputString = "{ someJson: \"change me\"}";
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
    
}
package com.revature.autoci.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GenerateProjectUtils {

    // Generates a .gitignore file by sending a GET request to gitignore.io
    // Creates request using the url then writes the response line by line to a .gitignore in the desired directory
    public static void generateGitIgnoreFromUrl(String gitIgnoreIoUrl, String directoryToPush) {
        try {
            URL requestURL;
            requestURL = new URL(gitIgnoreIoUrl);
            String readLine = null;
            String gitIgnorePath = directoryToPush + ".gitignore";
            File gitIgnoreFile = new File(gitIgnorePath);
            FileWriter writer = new FileWriter(gitIgnoreFile);

            // Creating request and making connection
            HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = connection.getResponseCode();

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

}

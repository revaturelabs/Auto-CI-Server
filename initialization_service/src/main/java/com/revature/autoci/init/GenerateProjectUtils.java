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

    public static void generateGitIgnoreFromUrl(String gitIgnoreIoUrl, String directoryToPush) {
        try {
            URL requestURL;
            requestURL = new URL(gitIgnoreIoUrl);
            String readLine = null;
            HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = connection.getResponseCode();
            String gitIgnorePath = directoryToPush + ".gitignore";
            File gitIgnoreFile = new File(gitIgnorePath);
            FileWriter writer = new FileWriter(gitIgnoreFile);

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

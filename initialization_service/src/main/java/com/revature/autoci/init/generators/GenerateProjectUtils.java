package com.revature.autoci.init.generators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateProjectUtils {

    private static final Logger log = LoggerFactory.getLogger(GenerateProjectUtils.class);

    // Generates a .gitignore file by sending a GET request to gitignore.io
    // Creates request using the url then writes the response line by line to a .gitignore in the desired directory
    public static void generateGitIgnoreFromUrl(String gitIgnoreIoUrl, String directoryToPush) {
        try {
            URL requestURL;
            requestURL = new URL(gitIgnoreIoUrl);
            String readLine = null;
            Path gitIgnorePath = Paths.get(directoryToPush, ".gitignore");
            File gitIgnoreFile = gitIgnorePath.toFile();
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
                log.info("Successfully received http response for .gitignote file");
            } else {
                log.warn("receiving http response for .gitignote file failed due to connection");
                System.err.println("Problem connecting to/receiving response from gitignore.io");
            }
        } catch (MalformedURLException e) {
            log.error("receiving http response for .gitignote file failed due to Malformed URL", e);
            e.printStackTrace();
            return;
        } catch (IOException e) {
            log.error("receiving http response for .gitignote file failed ", e);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            log.error("receiving http response for .gitignote file failed ", e);
             e.printStackTrace();
             return;
        }
    }

}

package com.revature.autoci.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelmGenerate {

    public static void generate() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        
        // check system
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        // writting command
        String buildcommand = "helm create mychart";

        //setting command
        if (isWindows) {

            processBuilder.command("cmd.exe", "/c", buildcommand);

        } else {

            processBuilder.command("sh", "-c", buildcommand);

        }

        //using command
        try {

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nBuilt with " + exitCode + " error(s).");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}


        // To Run:

        //creating new instance of Helm Generate: HelmGenerate test = new HelmGenerate();

        //Generating a new Help Chart" test.generate();
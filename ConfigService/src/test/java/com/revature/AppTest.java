package com.revature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.JSONObject;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void parseJsonToVarsTest() throws IOException 
    {
        ConfigServlet servlet1 = new ConfigServlet();
        GitHubAPI github = new GitHubAPI();
        boolean expectedDebugMode = true;
        JSONObject json = new JSONObject();

        String gitUsernameExpected = "New User";
        String jenkinsUriExpected = "user.com";
        String projNameExpected = "New Project";
        Boolean usingJenkinsExpected = false;
        Boolean debugModeExpected = true;

        json.put("githubUsername","New User");
        json.put("jenkinsURL","user.com");
        json.put("projectName","New Project");
        json.put("generateGithubActions",true);
        json.put("debug",true);

        servlet1.parseJsonToVars(json);

        assertEquals(gitUsernameExpected, servlet1.gitUsername);
        assertEquals(jenkinsUriExpected, servlet1.jenkinsUri);
        assertEquals(projNameExpected, servlet1.projName);
        assertEquals(usingJenkinsExpected, servlet1.usingJenkins);
        assertEquals(debugModeExpected, github.debugMode);
    }
    
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}

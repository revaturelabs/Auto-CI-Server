#example usage

- url endpoints
    http://a9922a23a32874c8e8f2509b9d044cd2-1946378861.us-east-1.elb.amazonaws.com/frontend
    http://a9922a23a32874c8e8f2509b9d044cd2-1946378861.us-east-1.elb.amazonaws.com/status

- sample input format that service api accepts in json passed to /frontend
```
{
  "githubUsername" : "mattbecker5",
  "githubURL" : "https://github.com/",
  "ide" : "visualstudiocode",
  "makeJenkinsWebhook" : true,
  "isMaven" : true,
  "isAzure" : false,
  "mavenData" : {
    "projectName" : "webapp",
    "version" : "1.0.0",
    "description" : "this is a test web app",
    "groupId" : "com.revature.webapp",
    "artifactId" : "com.revature.store",
    "packaging" : "jar",
    "javaVersion" : "1.8",
    "mainClass" : "App",
    "packageName" : "com.revature.webapp",
    "dependencies" : [ {
      "groupId" : "junit",
      "artifactId" : "junit:junit",
      "version" : "4.13.1"
    }, {
      "groupId" : "org.apache.tomcat",
      "artifactId" : "org.apache.tomcat:tomcat",
      "version" : "10.0.0-M9"
    } ]
  },
  "npmData" : {
    "projectName" : "",
    "version" : "1.0.0",
    "description" : "this is a test web app",
    "mainEntrypoint" : "main.js",
    "keywords" : [ "kubernetes", "AWS" ],
    "author" : "matt becker",
    "license" : "",
    "dependencies" : [ ],
    "devDependencies" : [ ],
    "scripts" : [ {
      "command" : "test",
      "script" : "echo \"Warning: no test specified\" && exit 0"
    } ]
  },
  "slackChannel" : "#slackchannel-spam"
}
```

- sample response to calling /status endpoint
- before pipline starts all will be set to "not started"
- then "started" when it starts a stage
- then "finished or failed" when complete

```
{
    "runningStatus": true,

    "azure": "not started",

    "configuration": "finished",

    "initialization": "finished",

    "jenkins": "finished",

    "spinnaker": "started"
}
```


Logger added for all current servelets. See below for additional information


# Logger usage
- The settings for the logger are configured within the logback.xml within the resources folder (needs to stay in the same folder as the webpage files, to my understanding).  Currently setup to create a new log file upon each execution of the program and will name that file with a date.  Note the sizing configuration within the logback.xml may need to change later if this application runs for long periods of time.  The log files will output into the log folder in the root of the project.  Ask Will or Lawrence if you need help changing this.


- Within the class in which you want to log, have the following imports:
    ```
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    ```
- Add the following line of code within the class in which you want to log, conventionally at the top:
    ```
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    ```
- Finally, you will signal the logger to output to the log file by having it use the various methods it has including trace(), debug(), info(), warn(), and error().  Example:
    ```
     public String view(String variable) {
        log.debug("The view method was run");
        ...
     }

    ```

    ```
     try {
         ...
     } catch (Exception e) {
        log.error("Exception!" + e);
     }
     
    ```
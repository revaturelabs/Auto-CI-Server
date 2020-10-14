# Overview of Spinnaker Service
Spinnaker Service is a simple web application ran on a tomcat server. That creates an application and pipeline on a running spinnaker instance. The pipeline configuration is provided via a Github repository that is provided on the post request.

# Endpoints
## [POST] /api/pipeline
### Description:
creates an application and pipeline on the connected running spinnaker instance.
### Sample Json
```
{
    "gitUri":"https://github.com/<organiztation>/<repo>.git",
    "cloudProviders":["kubernetes","AWS"],
    "email":"testemail",
    "projectName":"<repo_name>",
    "branch":"<branch_to_deploy_from>"
}
```

# Requirements

Spinnaker Service needs to be run in an enviroment that has the spin cli command line utility and communicates with a spinnaker cluster. 

The api endpoint or Spin gate endpoint for the Spinnaker instance should be added to the app.properties.
It should also be added to the and spin/config files if it will be ran in a conatiner.

# Usage
## Run Locally
### Be sure to see Requirements before running!
### Compile test package, build a docker image and run it localy with one command! 
Exposes on port 8080; 
```
bash dockerRun.sh
```
### Run locally manually:
### Be sure to see Requirements before running!
``` 
mvn package
docker build -t spintest .
docker run -it --rm -p 8080:8080 --name spin_service spintest 
```

## Test
Runs unit tests.

Code Coverage results are avalible as an html file at /target/site/jacoco/index.html
```
mvn test
```
## Deploy to Kubernetes
### Be sure to see Requirements before running!
Creates a a deployments with 2 replicasets and a loadbalancer listing on port 8080 for traffic.
 ```
 kubectl apply -f kubernetesYamlFiles/deployment_spinsvc.yaml
 kubectl apply -f kubernetesYamlFiles/service_spinsvc.yaml
 ```
# Logger usage
- The settings for the logger are configured within the logback.xml within the static folder (needs to stay in the same folder as the webpage files, to my understanding).  Currently setup to create a new log file upon each execution of the program and will name that file with a date.  Note the sizing configuration within the logback.xml may need to change later if this application runs for long periods of time.  The log files will output into the log folder in the root of the project.  Ask Will or Lawrence if you need help changing this.

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
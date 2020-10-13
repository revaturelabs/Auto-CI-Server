# Initialization Service
The initialiation service creates the necessary project files and directory structure for a Maven or Node.js project.

## Requirements
- An empty remote Git repository with a README file
- An OAuth token with read/write access to the remote Git repository.
- A Kubernetes cluster with the "autoci" namespace

## Endpoints
- [POST] /init/ - Initializes the github repository supplied in the HTTP request body. 

## Deployment Instructions
Create or apply a Kubernetes Secret in the cluster with the name "init-secrets". The Secret must have a data entry with key "github-token" that stores the base64 encoded OAuth token. Then apply all the manifest files in the `kubernetes_yaml` directory.

## Usage
Send a POST request to the `/init/` endpoint with a JSON body that matches the following format:  
```
{
    "githubUsername": "name",
    "githubURL": "https://github.com/name/repo",
    "isMaven": true,
    "ide": "visualstudiocode",
    "generateGithubActions": false, 
    // Include one of the following fields depending on the value of isMaven
    "mavenData": 
    {
        "projectName": "this-demo-project",
        "version": "0.0.0",
        "description": "the description of test-demo-project",
        "groupId": "com.test",
        "artifactId": "test-project",
        "packaging": "jar",
        "javaVersion": "1.8",
        "mainClass": "TestApp",
        "dependencies":[{"groupId":"com.ex", "artifactId":"example", "version":"1.5.0"}]
    },
    "npmData":
    {
        "projectName":"test-project",
        "version": "0.0.0",
        "description": "Test project",
        "mainEntrypoint": "main.js",
        "keywords": ["aws","kube"],
        "author": "author",
        "license" : "",
        "dependencies": [{"name":"example", "version":"^2.0.0"}],
        "devDependencies": [{"name":"example", "version":"^2.0.0"}], 
        "scripts": [{"command":"test", "script":"echo \"Error: no test specified\" && exit 1"}]
    }
}
```
On success, the server will return a 200 response code.

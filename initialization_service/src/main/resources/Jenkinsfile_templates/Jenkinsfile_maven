pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {
        stage('Pull') {
            steps {
                git url: "${githubURL}", branch: "${gitBranch}"
            }
        }
        stage('Checkstyle') {
            steps {
                sh 'mvn validate'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test -Dcheckstyle.skip=true'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn package -DskipTests=true -Dcheckstyle.skip=true'
            }
        }
        stage('Deploy') {
            steps {
                script {
                    docker.withTool('docker') {
                        repoId = "${dockerUser}/${projectName}"
                        image = docker.build(repoId)
                        docker.withRegistry("${ContainerRegistryURL}", "${ContainerRegistryCredId}") {
                            image.push()
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            slackSend(color: 'good', channel: "${slackChannel}", message: "Maven project '${projectName}' [${gitBranch}] has passed all tests and was successfully built and pushed to the CR.")
        }
        failure {
            slackSend(color: 'danger', channel: "${slackChannel}", message: "Maven project '${projectName}' [${gitBranch}] has failed to complete pipeline.")
        }
    }
}

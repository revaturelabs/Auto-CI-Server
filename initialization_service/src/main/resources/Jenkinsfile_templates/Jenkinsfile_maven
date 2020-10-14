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
        stage('Upload Docker Image') {
            steps {
                script {
                    docker.withTool('docker') {
                        repoId = "${projectName}"
                        image = docker.build(repoId)
                        docker.withRegistry("${ContainerRegistryURL}", "${ContainerRegistryCredId}") {
                            image.push()
                        }
                    }
                }
            }
        }
        stage('Helm Tests') {
            steps {
                withKubeConfig([credentialsId: "${kubectlCreds}", serverUrl: "${kubectlServer}"]) {
                    sh "helm install helmtest-${projectName}-${gitBranch} chart -n helm-test"
                    sleep(10);
                    sh 'helm test helmtest-${projectName}-${gitBranch} -n helm-test'
                }
            }
        }
        stage('Upload Helm Chart')
        {
            environment{
                chartname = sh (script: "helm package chart | sed 's/^[^:]*: //g'", returnStdout:true).trim()    
            }
            steps {
                sh "curl --data-binary \"@${env.chartname}\" ${helmChartRepoURL}"
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
        always {
            withKubeConfig([credentialsId: "${kubectlCreds}", serverUrl: "${kubectlServer}"]) {
                sh 'helm uninstall helmtest-${projectName}-${gitBranch} -n helm-test'
            }
        }
    }
}


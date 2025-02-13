properties([pipelineTriggers([githubPush()])])
pipeline{
    agent any 
          tools { 
        maven "M2_HOME" 
        }
        environment {
    registry = "arij/devops"
        registryCredential='dockerhub_id'
        dockerImage=''
         NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "172.16.65.5:8081"
        NEXUS_REPOSITORY = "maven-nexus-repo" 
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
    }
        stages {
            stage('Checkout GIT') {
    steps {
        echo 'Checking GitHub Repo...'
        script {
            try {
                git branch: 'main', url: 'https://github.com/Arij-Abid/foyer_projet.git'
            } catch (Exception e) {
                echo "Git Checkout failed: ${e.getMessage()}"
                error "Pipeline failed due to Git Checkout issue"
            }
        }
    }
}


            stage('git clone') {
            steps {
               git branch: 'main', 
               url: 'https://github.com/Arij-Abid/foyer_projet.git'
            }
        }

        

        
          stage('MVN CLEAN INSTALL'){
            steps {
                sh 'mvn clean install '
            }
        }
        stage ('JUnit / Mockito Test'){
            steps{
                sh 'mvn test'
                }
            }
        

         stage('SonarQube analysis') {
//    def scannerHome = tool 'SonarScanner 4.0';
        steps{
        withSonarQubeEnv('sonarqube-8.9.7') { 
        // If you have configured more than one global server connection, you can specify its name
//      sh "${scannerHome}/bin/sonar-scanner"
        sh "mvn sonar:sonar"
    }
        }
        } 

        


 stage("Publish to Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }     


stage("Maven Build") {
            steps {
                script {
                    sh "mvn package -DskipTests=true"
                }
            }
        } 

        
         stage('Building our image') { 
            steps { 
                script { 
                    dockerImage = docker.build registry + "arij" 
                }
            } 
        }
       stage('Deploy our image') { 
            steps { 
                script { 
                    docker.withRegistry( '', registryCredential ) { 
                        dockerImage.push() 
                    }
                } 
            }
        } 


stage('show Date') {
            steps {
                echo 'Showing Date...';
                sh """ date """;
            }
        }

         stage("Docker compose ") {
            steps {
                script {
                    sh "docker-compose up -d"
                }
            }
        } 
        

        /*
         stage('Building our image') { 
            steps { 
                script { 
                    dockerImage = docker.build registry + ":$BUILD_NUMBER" 
                }
            } 
        }
        */
   
       /*
        stage('Cleaning up') { 
            steps { 
                sh "docker rmi $registry:$BUILD_NUMBER" 
            }
        } */

       
}
 post {
            always{
                
                emailext to: "abidarij1@gmail.com",
                subject: "[DevOps Spring]jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
                body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\nMore Info can be found here: ${env.BUILD_URL}",
        attachLog: true
                
            }
        }   

} 

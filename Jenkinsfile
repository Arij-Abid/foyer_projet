properties([pipelineTriggers([githubPush()])])

pipeline {
    agent any 

    tools { 
        maven "M2_HOME" 
    }

    environment {
        registry = "arij/devops"
        DOCKERHUB_CREDENTIALS = credentials('dockerhub_id') 
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.1.19:8081"
        NEXUS_REPOSITORY = "maven-nexus-repo" 
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
    }

    stages {  
        
        stage('Checkout GIT') {
            steps {
                echo 'Checking GitHub Repo...'
                git branch: 'main', url: 'https://github.com/Arij-Abid/foyer_projet.git'
                sh 'ls -la'  // Ajout pour lister les fichiers
            }
        }

        stage('Docker Login') {
    steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub_id', usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASS')]) {
            sh 'echo $DOCKERHUB_PASS | docker login -u $DOCKERHUB_USER --password-stdin'
        }
    }
}


        stage('MVN CLEAN INSTALL') {
            steps {
               sh 'pwd'  // Affiche le chemin actuel
                sh 'ls -la'  // Liste les fichiers
                sh 'mvn clean install'
            }
        }

        stage('JUnit / Mockito Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonarqube-8.9.7') { 
                    sh "mvn sonar:sonar"
                }
            }
        }

        stage("Publish to Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml"
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}")
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    
                    artifactPath = filesByGlob[0].path
                    artifactExists = fileExists artifactPath
                    
                    if (artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}"
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId, classifier: '', file: artifactPath, type: pom.packaging],
                                [artifactId: pom.artifactId, classifier: '', file: "pom.xml", type: "pom"]
                            ]
                        )
                    } else {
                        error "*** File: ${artifactPath}, could not be found"
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
                    dockerImage = docker.build(registry + "arij") 
                }
            } 
        }

       stage('Deploy our image') { 
    steps { 
        script { 
            docker.withRegistry('', 'dockerhub_id') { 
                dockerImage.push() 
            }
        } 
    }
}


        stage('Show Date') {
            steps {
                echo 'Showing Date...'
                sh "date"
            }
        }

        stage("Docker Compose") {
            steps {
                script {
                    sh "docker-compose up -d"
                }
            }
        }
    } 

    post {
        always {
            emailext(
                to: "abidarij1@gmail.com",
                subject: "[DevOps Spring] Jenkins build: ${currentBuild.currentResult}: ${env.JOB_NAME}",
                body: "${currentBuild.currentResult}: Job ${env.JOB_NAME}\nMore Info can be found here: ${env.BUILD_URL}",
                attachLog: true
            )
        }
    }

} 

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
                sh 'ls -la'  // List files for debugging
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
                dir('Tp-Foyer') {  // Adjust this if necessary
                    sh 'mvn clean install'
                }
            }
        }

stage('JUnit / Mockito Test') {
    steps {
        dir('Tp-Foyer') {
            sh 'ls -la'  // Vérifier les fichiers présents
            sh 'mvn test'
        }
    }
}

stage('SonarQube analysis') {
    steps {
              dir('Tp-Foyer') {
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
                }
            }
        }
    }
}

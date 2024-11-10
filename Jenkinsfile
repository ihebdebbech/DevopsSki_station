pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS_ID = 'dockerCreds'
    }

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling...'
                git(
                    branch: 'mohamed',
                    url: 'https://github.com/ihebdebbech/DevopsSki_station.git'
                )
            }
        }

        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Install') {
            steps {
                sh 'mvn clean install -U'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn compile'
                echo 'compile done......'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Sonarqube') {
            steps {
                sh 'mvn test jacoco:report'
                sh "mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=SonarSonar123@"
            }
        }

        stage('Upload Artifact to Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true --settings /usr/share/maven/conf/settings.xml'
            }
        }

        stage('Building Docker Image') {
            steps {
                script {
                    echo 'Building Docker image...'
                    sh 'docker build -t chebbi4m/mohamedchebbiStationSkii:firstpush .'
                }
            }
        }

        stage('Pushing Docker Image to DockerHub') {
            steps {
                script {
                    echo 'Pushing Docker image to DockerHub...'
                    withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS_ID, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh 'docker push chebbi4m/mohamedchebbiStationSkii:firstpush'
                    }
                }
            }
        }

        stage('Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip'
            }
        }
    }
}

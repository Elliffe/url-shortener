pipeline {
    agent {
        dockerfile true
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvnw -B -DskipTests clean package'
                stash name: 'war', includes: 'target'
            }
        }
        stage('Test') {
            steps {
                unstash 'war'
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        // stage('Deliver') { 
        //     steps {
        //         sh './jenkins/scripts/deliver.sh' 
        //     }
        // }
    }
}

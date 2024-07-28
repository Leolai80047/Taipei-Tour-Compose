pipeline {
    agent {
        docker { image 'cimg/android:avd' }
    }
    environment {
        GITHUB_REPOSITORY = 'https://github.com/Leolai80047/Taipei-Tour-Compose.git'
    }
    stages {
        stage('Setup') {
          steps {
            sh "git submodule update --init --recursive"
            sh "cp /var/jenkins_home/keystores/TaipeiTour-Compose/taipeitour.keystore ./app/taipeitour.keystore"
            sh "cp /var/jenkins_home/keystores/TaipeiTour-Compose/secret.properties ./secret.properties"
            sh "cp /var/jenkins_home/keystores/TaipeiTour-Compose/google-services.json ./app/google-services.json"
            sh "cp /var/jenkins_home/keystores/TaipeiTour-Compose/firebase-private-key.json ./firebase-private-key.json"
            sh "chmod +x ./gradlew"
          }
        }
        stage('Build') {
          steps {
            sh './gradlew assembleRelease'
          }
        }
        stage('Deploy') {
          steps {
            sh './gradlew appDistributionUploadRelease'
          }
        }
    }
    post {
        always {
//             archiveArtifacts artifacts: 'app/build/outputs/bundle/release/app-release.aab', fingerprint: true
            cleanWs()
        }
    }
}
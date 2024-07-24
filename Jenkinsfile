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
            sh 'git submodule update --init --recursive'
            sh 'git checkout feature/CI_TEST'
            sh 'cp /var/jenkins_home/keystores/TaipeiTour-Compose/taipeitour.keystore ./app/taipeitour.keystore'
            sh 'cp /var/jenkins_home/keystores/TaipeiTour-Compose/secret.properties ./secret.properties'
            sh 'chmod +x ./gradlew'
          }
        }
        stage('Build') {
          steps {
            sh './gradlew bundleRelease'
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
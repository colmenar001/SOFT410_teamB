pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew --no-daemon --console=plain clean assemble'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew --no-daemon --console=plain test'
            }
            post {
                always {
                    junit testResults: 'build/test-results/test/TEST-*.xml'
                    archiveArtifacts artifacts: 'build/reports/tests/test/**'
                }
            }
        }
    }
}

def call(Map config = [:]) {
    pipeline {
        agent any

        environment {
            APP_NAME        = config.appName ?: "demo-app"
            DOCKER_REGISTRY = config.registry ?: "docker.io"
            IMAGE_TAG       = "${env.BUILD_NUMBER}"
        }

        stages {

            stage('Checkout') {
                steps {
                    gitCheckout(config)
                }
            }

            stage('Build') {
                steps {
                    buildApp(config)
                }
            }

            stage('Unit Tests') {
                steps {
                    unitTest(config)
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    sonarScan(config)
                }
            }

            stage('Quality Gate') {
                steps {
                    qualityGate()
                }
            }

            stage('Trivy Security Scan') {
                steps {
                    trivyScan(config)
                }
            }

            stage('Docker Build & Push') {
                steps {
                    dockerBuildPush(config)
                }
            }

            stage('Deploy') {
                steps {
                    deployApp(config)
                }
            }
        }

        post {
            failure {
                echo "Pipeline failed. Please check logs."
            }
            success {
                echo "Pipeline completed successfully."
            }
        }
    }
}

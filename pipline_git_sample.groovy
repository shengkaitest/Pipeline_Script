pipeline {
    agent any
    stages {
        stage('Git-Checkout') {
            steps {
                    echo "Checking out from Git Repo";
                    git 'https://github.com/shengkaitest/Pipeline_Script.git'
                    sh 'chmod -R 777 ./'
            }
        }
    
        stage('Build') {
            steps {
                    echo "Building the checked-out project!";
                    sh returnStdout: true, script: '${WORKSPACE}/Build.sh'
                    sh './Build.sh'
            }
        }
    
        stage('Unit-Test') {
            steps {
                    echo "Running JUnit Tests";
                    sh './Unit.sh'
            }
        }
    
        stage('Quality-Gete') {
            steps {
                    echo "Verifying Quality Gates";
                    sh './Quality.sh'
            }
        }

        stage('Deploy') {
            steps {
                    echo "Deploying to Stage Evironment for more tests";
                    sh './Deploy.sh'
            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipline has changed'
            echo 'For example, if the Pipline was previusly failing but is now successful'
        }
    }
}
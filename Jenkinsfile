pipeline {
  agent any

  stages {
    stage('Setup') {
      steps {
        sh 'find src/build/test-results -name "*xml" -delete'
      }
    }
      
    stage('Test') {
      steps {
        echo 'Testing..'
	sh 'cd src; gradle test'
      }
    }
  }
    
  post {
    always {
      junit allowEmptyResults: true, keepLongStdio: true, testResults: 'src/build/test-results/**/*.xml'
    }
  }
}

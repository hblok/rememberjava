pipeline {
  agent any

  stages {
    stage('Test') {
      steps {
        echo 'Testing..'
	sh 'cd src; gradle test'
      }
    }
  }
}

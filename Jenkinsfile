pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        echo 'Building..'
	sh 'javac src/HelloWorld.java -d bin'
	sh 'ls bin'
      }
    }
  }
}

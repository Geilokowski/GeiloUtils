pipeline {
  agent any
  stages {
    stage('Setup CI Environment') {
      steps {
        sh 'mvn clean'
        sh 'mvn compile'
      }
    }
    stage('Artifact') {
      steps {
        archiveArtifacts '**/target/*.jar'
      }
    }
  }
}
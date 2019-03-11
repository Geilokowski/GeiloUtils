pipeline {
  agent any
  stages {
    stage('Setup CI Environment') {
      steps {
        withMaven(maven: 'm3') {
          sh 'mvn clean package'
        }

      }
    }
    stage('Artifact') {
      steps {
        archiveArtifacts '**/target/*.jar'
      }
    }
  }
}
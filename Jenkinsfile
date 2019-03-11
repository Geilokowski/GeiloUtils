pipeline {
  agent any
  stages {
    stage('Setup CI Environment') {
      steps {
        withMaven(mavenSettingsConfig: 'pom', mavenSettingsFilePath: './') {
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
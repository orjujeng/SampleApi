pipeline {
  agent {
    node {
      label 'maven'
    }

  }
  stages {
    stage('github-pull & complie') {
      steps {
        git(credentialsId: 'github', url: 'https://github.com/orjujeng/Timesheet.git', branch: 'master', changelog: true, poll: false)
        container('maven') {
          sh 'mvn clean install'
        }

      }
    }

    stage('build & push') {
      agent none
      steps {
        container('maven') {
          sh 'mvn -o -Dmaven.test.skip=true clean package'
          sh 'docker build --no-cache -f Dockerfile -t $REGISTRY/orjujeng/timesheet:test .'
          withCredentials([usernamePassword(credentialsId : 'docker' ,passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,)]) {
            sh 'echo "$DOCKER_PASSWORD" | docker login $REGISTRY -u "$DOCKER_USERNAME" --password-stdin'
            sh 'docker push  $REGISTRY/orjujeng/timesheet:test'
          }

        }

      }
    }
     stage('deploy to dev') {
             steps {
                 container ('maven') {
                      withCredentials([
                          kubeconfigFile(
                          credentialsId: env.KUBECONFIG_CREDENTIAL_ID,
                          variable: 'KUBECONFIG')
                          ]) {
                          sh 'envsubst < deploy/k8s.yaml | kubectl apply -f -'
                      }
                 }
             }
        }
    }
  environment {
    DOCKER_CREDENTIAL_ID = 'dockerhub-id'
    GITHUB_CREDENTIAL_ID = 'github-id'
    KUBECONFIG_CREDENTIAL_ID = 'kubeconfig'
    REGISTRY = 'docker.io'
    DOCKERHUB_NAMESPACE = 'orjujeng'
    GITHUB_ACCOUNT = 'orjujeng'
    DOCKER_USERNAME = 'orjujeng'
  }
}
pipeline {
    agent {
        label 'JavaAgent'
    }
    parameters {
        booleanParam(name: 'SKIP_PUSH', defaultValue: false)
        booleanParam(name: 'CREATE_CREDS', defaultValue: false)
        choice(name: 'PROFILE', choices: ['dev', 'vm'], description: 'Spring Profile')
    }
    environment {
        ARTIFACT_ID=readMavenPom().getArtifactId()
        PROJECT_VERSION=readMavenPom().getVersion()
        IMAGE_NAME="$NEXUS_1/edu.mv/$ARTIFACT_ID:$PROJECT_VERSION"
        NEXUS_PASSWORD=credentials('DEPLOY_USER_PASSWORD')
        DEPLOYMENT_DIR="/home/user1/${EQUIPE}/${PROFILE}"
        DEPLOY_SERVER="10.10.0.42"
        EQUIPE="eq5"
    }
    stages {
        stage("Clean") {
            steps {
                sh 'mvn clean install'
            }
        }
//         stage("Deploy") {
//             steps {
//                 sh 'mvn clean deploy -s ./settings.xml'
//             }
//         }
        stage("Build image") {
            steps {
                echo 'Building Image ...'
                sh "docker build . -t $IMAGE_NAME --build-arg SPRING_PROFILE=$PROFILE"
            }
        }
        stage("Inspect Jacoco Coverage"){
            steps {
                 recordCoverage(tools: [[parser: 'JACOCO']],
                         id: 'jacoco', name: 'JaCoCo Coverage',
                         sourceCodeRetention: 'EVERY_BUILD',
                         qualityGates: [
                                 [threshold: 60.0, metric: 'LINE', baseline: 'PROJECT', unstable: true],
                                 [threshold: 60.0, metric: 'BRANCH', baseline: 'PROJECT', unstable: true]
                         ]
                 )
            }
        }
        stage("Push image") {
            when {
                expression {
                   params.SKIP_PUSH == false
                }
            }
            steps {
                sh 'echo $NEXUS_PASSWORD | docker login $NEXUS_1 --username $NEXUS_DOCKER_USERNAME --password-stdin'
                sh "docker push $IMAGE_NAME"
            }
        }
        stage("Deploy Minikube") {
            steps{
              script {
                sh "ssh ${USER_KUBE_1}@${DEPLOY_SERVER} 'rm -rf ${EQUIPE}'"
                sh "ssh ${USER_KUBE_1}@${DEPLOY_SERVER} 'mkdir ${EQUIPE}'"
                sh "scp -r ./k8s/${PROFILE} ${DEPLOY_SERVER}:/home/${USER_KUBE_1}/${EQUIPE}"
                sh "ssh ${USER_KUBE_1}@${DEPLOY_SERVER} minikube kubectl -- apply -f ${DEPLOYMENT_DIR}/namespace.yml"
                if (env.CREATE_CREDS == true) {
                    sh "ssh ${USER_KUBE_1}@${DEPLOY_SERVER} minikube kubectl -- create secret docker-registry regcred --docker-server=${NEXUS_1} --docker-username=${NEXUS_DOCKER_USERNAME} --docker-password=${NEXUS_PASSWORD} --docker-email=de@deploy.com --namespace=eq5"       
                }
                sh "ssh ${USER_KUBE_1}@${DEPLOY_SERVER} minikube kubectl -- apply -f ${DEPLOYMENT_DIR}/deploy/ --namespace=eq5"
              }
            }
        }
    }
}
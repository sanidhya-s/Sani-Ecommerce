//pipeline {
//    agent any
//
//    environment {
//        IMAGE_NAME = "my-springboot-app"
//        CONTAINER_NAME = "springboot-container"
//    }
//
//    tools {
//        maven 'Maven-3.8.1'
//    }
//
//    stages {
//        stage('Checkout') {
//            steps {
//                git branch: 'main', url: 'https://github.com/sanidhya-s/Sani-Ecommerce.git'
//            }
//        }
//
//        stage('Build with Maven') {
//            steps {
//                sh 'mvn clean package -DskipTests'
//            }
//        }
//
//        stage('Build Docker Image') {
//            steps {
//                script {
//                    // Delete old image if it exists
//                    sh """
//                        if docker images | grep -q $IMAGE_NAME; then
//                            docker rmi -f $IMAGE_NAME:latest || true
//                        fi
//                    """
//
//                    // Build new image
//                    sh "docker build -t $IMAGE_NAME:latest ."
//                }
//            }
//        }
//
//        stage('Deploy Container') {
//            steps {
//                script {
//                    // Stop and remove old container if running
//                    sh """
//                        if [ \$(docker ps -q -f name=$CONTAINER_NAME) ]; then
//                            docker stop $CONTAINER_NAME
//                            docker rm $CONTAINER_NAME
//                        elif [ \$(docker ps -aq -f name=$CONTAINER_NAME) ]; then
//                            docker rm $CONTAINER_NAME
//                        fi
//                    """
//
//                    // Start new container
//                    sh "docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME:latest"
//                }
//            }
//        }
//    }
//}



pipeline {
	agent any

    environment {
		IMAGE_NAME     = "my-springboot-app"
        AWS_ACCOUNT_ID = "582739605818"        // Replace with your AWS account ID
        AWS_REGION     = "ap-south-1"                 // Replace with your AWS region
        ECR_REPO       = "ci-cd"          // Replace with your ECR repo name
        CONTAINER_NAME = "springboot-container"
    }

    tools {
		maven 'Maven-3.8.1'
    }

    stages {
		stage('Checkout') {
			steps {
				git branch: 'main', url: 'https://github.com/sanidhya-s/Sani-Ecommerce.git'
            }
        }

        stage('Build with Maven') {
			steps {
				sh 'mvn clean package -DskipTests'
            }
        }

        stage('Login to AWS ECR') {
			steps {
				script {
					// Authenticate Docker with ECR
                    sh """
                        aws ecr get-login-password --region $AWS_REGION \
                        | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
                    """
                }
            }
        }

        stage('Build & Push Docker Image') {
			steps {
				script {
					def ecr_image = "$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO:latest"

                    // Delete old local image if exists
                    sh """
                        if docker images | grep -q $IMAGE_NAME; then
                            docker rmi -f $IMAGE_NAME:latest || true
                        fi
                    """

                    // Build new image
                    sh "docker build -t $IMAGE_NAME:latest ."

                    // Tag the image for ECR
                    sh "docker tag $IMAGE_NAME:latest $ecr_image"

                    // Push to ECR
                    sh "docker push $ecr_image"
                }
            }
        }
    }
}

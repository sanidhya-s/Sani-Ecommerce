# Use an official JDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set application JAR name
ARG JAR_FILE=target/*.jar

# Copy JAR into the container
COPY ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
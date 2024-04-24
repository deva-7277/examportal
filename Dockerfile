#start with a base image containing java runtime
FROM openjdk:18-slim as build

#Information about who maintains image
MAINTAINER Devanand Kale

#Add the application's jar to the container
COPY target/examserver-0.0.1-SNAPSHOT.jar examserver-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar", "/examserver-0.0.1-SNAPSHOT.jar"]
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG BUILD_PATH
COPY ${BUILD_PATH}/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
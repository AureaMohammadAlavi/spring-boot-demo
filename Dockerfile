FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
COPY target/spring-demo-1.0-SNAPSHOT.jar app.jar
COPY ${WORKDIR}/target/spring-demo-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
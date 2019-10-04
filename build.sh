#!/bin/sh

cd app
./mvnw package
cp target/*.jar ../build/spring-boot-app.jar
 
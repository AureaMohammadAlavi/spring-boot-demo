#!/bin/sh


export ROOT_FOLDER=$( pwd )

M2_HOME="${HOME}/.m2"
M2_CACHE="${ROOT_FOLDER}/maven"

echo "M2_HOME: ${M2_HOME}"
echo "M2_CACHE: ${M2_CACHE}"

echo "Generating symbolic link for caches"
[[ -d "${M2_CACHE}" && ! -d "${M2_HOME}" ]] && ln -s "${M2_CACHE}" "${M2_HOME}"


cd app
./mvnw package
cp target/*.jar ../build/spring-boot-app.jar
ls -la ../build
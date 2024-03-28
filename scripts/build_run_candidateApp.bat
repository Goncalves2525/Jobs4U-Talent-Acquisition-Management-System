@echo off

cd ..

:: Build
mvn clean package

:: Change Directory
cd jobs4u.candidateApp\target

:: Run
java -jar jobs4u.candidateApp-0.1.0.jar

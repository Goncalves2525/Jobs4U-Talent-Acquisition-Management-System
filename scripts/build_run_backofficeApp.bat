@echo off

cd ..

:: Build
mvn clean package

:: Change Directory
cd jobs4u.backofficeApp\target

:: Run
java -jar jobs4u.backofficeApp-0.1.0.jar

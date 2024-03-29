@echo off

cd ..

:: Build
call mvn clean package

:: Change Directory
cd jobs4u.customerApp\target

:: Run
java -jar jobs4u.customerApp-0.1.0.jar

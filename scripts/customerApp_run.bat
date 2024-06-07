@echo off

:: Classpath: jar;dependencies
SET APP_classpath=jobs4u.customerApp\target\jobs4u.customerApp-0.1.0.jar;jobs4u.customerApp\target\dependency\*

:: Main Class: package.EntryClass
SET APP_class=CustomerApp

:: Change directory
cd ..

:: Run
java -classpath %APP_classpath% %APP_class%

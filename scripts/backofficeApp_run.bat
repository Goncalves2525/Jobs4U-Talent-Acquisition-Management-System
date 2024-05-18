@echo off

:: Classpath: jar;dependencies
SET APP_classpath=jobs4u.backofficeApp\target\jobs4u.backofficeApp-0.1.0.jar;jobs4u.backofficeApp\target\dependency\*

:: Main Class: package.EntryClass
SET APP_class=BackOffice

:: Change directory
cd ..

:: Run
java -classpath %APP_classpath% %APP_class%

@echo off

:: Change Directory
cd ..

:: Build
mvn %1 package dependency:copy-dependencies -Dmaven.compiler.source=17 -Dmaven.compiler.target=17 -pl jobs4u.backofficeApp -am

#!/bin/bash

# Classpath: jar;dependencies
APP_classpath="jobs4u.candidateApp/target/jobs4u.candidateApp-0.1.0.jar:jobs4u.candidateApp/target/dependency/*"

# Main Class: package.EntryClass
APP_class=Candidate

# Change directory
cd ..

# Run
java -classpath $APP_classpath $APP_class
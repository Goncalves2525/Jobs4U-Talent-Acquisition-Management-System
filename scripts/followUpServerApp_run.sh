#!/bin/bash

# Classpath: jar;dependencies
APP_classpath="jobs4u.followUpServer/target/jobs4u.followUpServer-0.1.0.jar:jobs4u.followUpServer/target/dependency/*"

# Main Class: package.EntryClass
APP_class=FollowUpServerApp

# Change directory
cd ..

# Run
java -classpath $APP_classpath $APP_class
#!/bin/bash
# Script to start H2 Console for viewing database

echo "Starting H2 Console..."
java -cp ~/.m2/repository/com/h2database/h2/2.2.224/h2-2.2.224.jar org.h2.tools.Console

# This will open a browser window where you can connect to the database
# Use these connection details:
# JDBC URL: jdbc:h2:./db/jobs4u
# User Name: (leave empty)
# Password: (leave empty)

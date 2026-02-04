#!/bin/bash
# Script to check database contents

echo "Checking if database exists..."
if [ ! -f "./db/jobs4u.mv.db" ]; then
    echo "ERROR: Database file not found at ./db/jobs4u.mv.db"
    echo "Make sure you ran BackOffice with bootstrap first!"
    exit 1
fi

echo "Database file found!"
echo ""
echo "Opening H2 Console to view database..."
echo "In the browser that opens, use these connection details:"
echo "  JDBC URL: jdbc:h2:./db/jobs4u"
echo "  User Name: (leave empty)"
echo "  Password: (leave empty)"
echo ""
echo "Then run this SQL query to see all users:"
echo "  SELECT EMAIL, PASSWORD, ROLE, ABILITY FROM APPUSER;"
echo ""

java -cp ~/.m2/repository/com/h2database/h2/2.2.224/h2-2.2.224.jar org.h2.tools.Console

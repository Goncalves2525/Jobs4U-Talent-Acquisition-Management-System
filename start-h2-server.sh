#!/bin/bash
# Script to start H2 Database Server in TCP mode
# This allows multiple applications to share the same database

echo "Starting H2 Database Server..."
echo "Server will run on port 9092"
echo "Database location: $(pwd)/db/"
echo ""
echo "Keep this terminal open while using the applications."
echo "Press Ctrl+C to stop the server."
echo ""

java -cp ~/.m2/repository/com/h2database/h2/2.2.224/h2-2.2.224.jar org.h2.tools.Server \
  -tcp -tcpAllowOthers -tcpPort 9092 \
  -baseDir "$(pwd)/db" \
  -ifNotExists

#!/bin/bash

# Change Directory
cd ..

# Build
mvn $1 package dependency:copy-dependencies -pl jobs4u.backofficeApp -am
#!/bin/bash

export PORT=8080
echo starting server on port $PORT
cd /usr/share/webapp/
java -jar /usr/share/webapp/target/webapi-1.0-SNAPSHOT-jar-with-dependencies.jar

#!bin/bash
mvn package
docker build -t spintest .
docker run -it --rm -p 8080:8080 --name spin_service spintest
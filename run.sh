#!/bin/bash
mvn package
docker stop file-processor-web
docker rm file-processor-web
docker rmi anish/fileprocessor:v1
docker image prune -f
docker build . -t anish/fileprocessor:v1
docker run --name file-processor-web -d -p 8080:8080 anish/fileprocessor:v1

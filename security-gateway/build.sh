#!/bin/bash

echo "Performing a clean Maven build"
./mvnw clean package -DskipTests=true

echo "Building the Gateway"
cd security-gateway
docker rmi scg-bcp-security-gateway:latest
docker build --tag scg-bcp-security-gateway .
cd ..

echo "Building the Service"
cd secured-service
docker rmi scg-bcp-secured-service:latest
docker build --tag scg-bcp-secured-service .
cd ..

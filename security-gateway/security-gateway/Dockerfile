FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine
COPY target/securitygateway-0.0.1-SNAPSHOT.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
EXPOSE 8081
# Maven build container 
FROM openjdk:8-jre-alpine

COPY target/employer-service-0.0.1-SNAPSHOT.jar employer-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/employer-service.jar"]
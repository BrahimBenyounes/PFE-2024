# Package stage
FROM openjdk:17
COPY target/stock-service-0.0.1-SNAPSHOT.jar /usr/local/lib/stock-service.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/usr/local/lib/stock-service.jar"]

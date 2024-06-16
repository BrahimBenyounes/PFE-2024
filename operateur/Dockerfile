
FROM openjdk:17
COPY target/*.jar /usr/local/lib/operateur-service.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "/usr/local/lib/operateur-service.jar"]

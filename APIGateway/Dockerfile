
FROM openjdk:8
COPY target/*.jar /usr/local/lib/gateway-service.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/usr/local/lib/gateway-service.jar"]

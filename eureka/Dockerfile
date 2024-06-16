FROM openjdk:8
COPY target/*.jar /usr/local/lib/discovery.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/usr/local/lib/discovery.jar"]
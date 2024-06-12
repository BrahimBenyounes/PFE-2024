# Build stage
FROM maven:3.6.0-jdk-11-slim AS build

# Copy the Maven settings.xml with Nexus configuration
COPY settings.xml /usr/share/maven/ref/settings.xml

WORKDIR /home/app
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -B -s /usr/share/maven/ref/settings.xml -Dmaven.test.skip=true clean package

# Package stage
FROM openjdk:8

COPY --from=build /home/app/target/discovery-0.0.1-SNAPSHOT.jar /usr/local/lib/discovery.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/usr/local/lib/discovery.jar"]

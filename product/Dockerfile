# Use Maven 3.6 with OpenJDK 17 for the build stage
FROM maven:3.6-openjdk-17 as builder

# Copy source code and the pom.xml to the container
COPY src /home/app/src
COPY pom.xml /home/app

# Package the application, skipping tests
RUN mvn -q -Dmaven.test.skip=true -f /home/app/pom.xml clean package

# Use OpenJDK 8 for the runtime stage
FROM openjdk:17

# Copy the jar file from the builder stage
COPY --from=builder /home/app/target/product-service-0.0.1-SNAPSHOT.jar /usr/local/lib/product-service.jar

# Expose the application port
EXPOSE 8087

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "/usr/local/lib/product-service.jar"]

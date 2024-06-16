
FROM openjdk:17

# Copy the jar file from the builder stage
COPY target/product-service-0.0.1-SNAPSHOT.jar /usr/local/lib/product-service.jar

# Expose the application port
EXPOSE 8087

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "/usr/local/lib/product-service.jar"]

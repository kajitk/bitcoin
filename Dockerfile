# Use a base image with OpenJDK 17
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/bitcoin-0.0.1-SNAPSHOT.jar /app/bitcoin.jar

# Expose the port that the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "bitcoin.jar"]

# Use the base image of OpenJDK 17
FROM openjdk:17-alpine

# Create a volume for the logs (optional)
VOLUME /tmp

# Copy the JAR file into the Docker image
COPY target/gestion-station-skii-0.0.1-SNAPSHOT.jar /app/gestion-station-skii.jar

# Expose the port that the application listens on
EXPOSE 8082

# Start the application
ENTRYPOINT ["java", "-jar", "/app/gestion-station-skii.jar"]
# Use an official Amazon Corretto 17 runtime as a parent image
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app

# Copy the renamed JAR file into the container
ADD target/*.jar /app/app.jar

# Expose the port your Spring Boot application will listen on
EXPOSE 8080

# Start the Spring Boot application
CMD ["java", "-Dspring.devtools.restart.enabled=true", "-jar", "todolist.jar"]
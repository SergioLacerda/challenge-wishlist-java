# Use Maven 3.8.6 and Java 17 base image
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download project dependencies (this step is cached)
COPY pom.xml .

# Download Maven dependencies (this is a separate layer to cache it)
RUN mvn dependency:go-offline -B

# Copy the rest of the project files
COPY src ./src

# Package the application to generate a jar file
RUN mvn clean package -DskipTests

# Use a smaller JDK base image for running the app
FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (adjust if needed)
EXPOSE 8080

# Set the entry point to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

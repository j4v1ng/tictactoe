FROM gradle:8.5-jdk17 AS build

WORKDIR /app
COPY . /app/
# Use the Gradle wrapper to ensure we use the correct Gradle version
RUN chmod +x ./gradlew && ./gradlew build --no-daemon

FROM openjdk:17-slim

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
# Copy the resources directory to ensure application.yaml and other resources are available
COPY --from=build /app/build/resources/main /app/resources

EXPOSE 8080

# Run the application with the correct classpath to find resources
CMD ["java", "-cp", "/app/app.jar:/app/resources", "io.ktor.server.netty.EngineMain"]

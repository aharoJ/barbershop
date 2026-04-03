# Dev stage
FROM eclipse-temurin:25-jdk-alpine AS dev
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle
RUN ./gradlew dependencies --no-daemon
COPY src src
CMD ["./gradlew", "bootRun", "--no-daemon"]

# Build stage
FROM dev AS build
RUN ./gradlew bootJar --no-daemon

# Prod stage
FROM eclipse-temurin:25-jre-alpine AS prod
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

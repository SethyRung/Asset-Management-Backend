# -------- Build stage --------
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy only gradle wrapper and build files first (better caching)
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

# Ensure gradlew is executable
RUN chmod +x gradlew

# Download dependencies (cached if no changes)
RUN ./gradlew dependencies --no-daemon || return 0

# Copy the actual source code
COPY src ./src

# Build the application
RUN ./gradlew clean build --no-daemon --build-cache

# -------- Runtime stage --------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Create a non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expose port
EXPOSE 8080

# Default JVM options (tunable via env)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

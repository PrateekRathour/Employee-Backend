# Multi-stage Dockerfile for Spring Boot Microservice
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /build

# Copy Maven wrapper and dependencies specification
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Pre-fetch dependencies to leverage Docker cache
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B || true

# Copy source code and build production jar
COPY src src
RUN ./mvnw clean package -DskipTests -B

# Stage 2: Minimal Distroless / JRE Runtime container
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Add non-root system group and user for security compliance
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /build/target/*.jar app.jar

RUN chown -R appuser:appgroup /app
USER appuser

EXPOSE 8080

# Configure JVM tuning for containerized cloud deployment (AWS ECS / EC2)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]

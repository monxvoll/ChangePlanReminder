# --- Etapa 1: Construcción ---
FROM maven:3.9.6-eclipse-temurin-8 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package

# --- Etapa 2: Ejecución ---
FROM openjdk:8-jre-slim
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

COPY --from=builder /app/target/lib /app/lib

CMD ["java", "-jar", "app.jar"]

FROM maven:3.9.6-eclipse-temurin-8 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:8-jre-slim
WORKDIR /app

RUN apt-get update && apt-get install -y unzip

COPY --from=builder /app/target/*.jar app.jar

CMD ["/bin/sh", "-c", "echo '--- Contenido del MANIFEST.MF ---' && unzip -p app.jar META-INF/MANIFEST.MF && echo '--- Fin del Contenido ---' && echo 'Build en pausa para depuración...' && sleep 3600"]
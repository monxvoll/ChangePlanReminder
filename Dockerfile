# --- Etapa 1: Construcción (Línea FROM corregida) ---
FROM maven:3.9.6-eclipse-temurin-8 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package

# --- Etapa 2: Ejecución con Depuración (Sin cambios) ---
FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Mantenemos el comando de depuración para inspeccionar el .jar
CMD ["/bin/sh", "-c", "echo '--- Contenido del MANIFEST.MF ---' && unzip -p app.jar META-INF/MANIFEST.MF && echo '--- Fin del Contenido ---' && echo 'Build en pausa para depuración...' && sleep 3600"]
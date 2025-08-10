# --- Etapa 1: Construcción (No cambia) ---
FROM maven:3.9.6-jdk-8-slim AS builder
WORKDIR /app
COPY . .
RUN mvn clean package

# --- Etapa 2: Ejecución con Depuración ---
FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# ==========================================================
# === COMANDO DE DEPURACIÓN: NO EJECUTA, SOLO INSPECCIONA ===
# ==========================================================
# Esta línea inspecciona el archivo MANIFEST.MF dentro del .jar y luego
# deja el contenedor "dormido" para que podamos ver los logs.
CMD ["/bin/sh", "-c", "echo '--- Contenido del MANIFEST.MF ---' && unzip -p app.jar META-INF/MANIFEST.MF && echo '--- Fin del Contenido ---' && echo 'Build en pausa para depuración...' && sleep 3600"]
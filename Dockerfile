
FROM maven:3.8.6-jdk-17


WORKDIR /app

COPY . .

RUN mvn clean package

EXPOSE 8080

CMD ["java", "-jar", "target/ChangePlanReminder.jar"]

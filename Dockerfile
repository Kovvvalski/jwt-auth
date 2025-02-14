FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY target/jwt-auth-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# -------- STAGE 1: Build --------
FROM gradle:8.7-jdk21 AS builder

WORKDIR /app
COPY . .

RUN gradle clean build -x test

# -------- STAGE 2: Runtime --------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
FROM gradle:jdk23 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM openjdk:23-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/iron-jelly-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
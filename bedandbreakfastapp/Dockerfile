FROM maven:eclipse-temurin AS builder

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /src/target/assessment-0.0.1-SNAPSHOT.jar app.jar

ENV SPRING_DATASOURCE_URL = 
ENV SPRING_DATA_MONGODB_URI=
# ENV SPRING_DATASOURSE_USERNAME = 
# ENV SPRING_DATASOURCE_PASSWORD = 

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar
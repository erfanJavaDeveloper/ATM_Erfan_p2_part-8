#FROM openjdk:18-jdk-alpine3.9
FROM amazoncorretto:18-alpine-jdk
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]
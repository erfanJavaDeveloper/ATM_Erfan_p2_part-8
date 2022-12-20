FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./app
WORKDIR /app
CMD openjdk app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
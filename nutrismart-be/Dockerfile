FROM openjdk:8-jdk-slim
WORKDIR /service
COPY ./target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]
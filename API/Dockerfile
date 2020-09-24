FROM openjdk:8-jdk-alpine
COPY target/api-1.0-SNAPSHOT-jar-with-dependencies.jar ./target/Application.jar
COPY src/main/java/project3api* src/main/java/project3api/view/
ENTRYPOINT java -jar target/Application.jar

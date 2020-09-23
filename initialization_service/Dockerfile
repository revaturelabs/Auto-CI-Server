FROM openjdk:8-jdk
COPY target/initialization-service-0.0.1.jar ./server.jar
ENTRYPOINT java -jar -DGITHUB_TOKEN="$GITHUB_TOKEN" server.jar

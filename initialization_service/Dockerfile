FROM alpine/helm:3.3.4
RUN apk add --no-cache openjdk8-jre-base
COPY target/initialization-service-1.0.0.jar ./server.jar
ENTRYPOINT java -jar server.jar

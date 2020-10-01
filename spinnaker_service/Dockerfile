FROM openjdk:8-jdk-buster
#RUN apt install curl -y
RUN curl -LO https://storage.googleapis.com/spinnaker-artifacts/spin/$(curl -s https://storage.googleapis.com/spinnaker-artifacts/spin/latest)/linux/amd64/spin
RUN chmod +x spin
RUN mv spin /usr/local/bin/spin
RUN mkdir /root/.spin/
RUN apt update && apt install screen -y
COPY spin/config /root/.spin/config
COPY src/main/resources/* src/main/resources/
COPY target/spinnaker_service-1.0-SNAPSHOT-jar-with-dependencies.jar ./target/Application.jar
COPY app.properties app.properties
ENTRYPOINT java -jar target/Application.jar

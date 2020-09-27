
FROM debian:latest
RUN apt-get -y update
RUN apt-get -y install openjdk-11-jdk-headless
COPY . /usr/share/webapp/
EXPOSE 8080
COPY entrypoint.sh /usr/share/scripts/entrypoint.sh
ENTRYPOINT ["sh", "/usr/share/scripts/entrypoint.sh"]
FROM tomcat:latest
ADD target/AzureService.war /usr/local/tomcat/webapps/

# Get Azure CLI
RUN curl -sL https://aka.ms/InstallAzureCLIDeb | bash
RUN rm -rf /var/lib/apt/lists/*

CMD ["catalina.sh", "run"]
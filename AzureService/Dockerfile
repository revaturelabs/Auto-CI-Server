FROM tomcat:latest

# Get Azure CLI
RUN curl -sL https://aka.ms/InstallAzureCLIDeb | bash
RUN rm -rf /var/lib/apt/lists/*
RUN az extension add --name azure-devops

ADD target/AzureService.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]
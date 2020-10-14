FROM tomcat:latest

# Get Terraform
RUN wget https://releases.hashicorp.com/terraform/0.13.4/terraform_0.13.4_linux_amd64.zip
RUN unzip terraform_0.13.4_linux_amd64.zip
RUN mv terraform /usr/local/bin/

ADD target/jenkins-svlt.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]
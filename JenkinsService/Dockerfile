FROM jenkins/jenkins:lts

# Vars:
ENV JAVA_OPTS "-Djenkins.install.runSetupWizard=false"
ENV JENKINS_USER admin
ENV JENKINS_PASS admin
ENV PLUGIN_DEST /usr/share/jenkins/ref/plugins.txt
# ENV JOBDSL_DEST /usr/share/jenkins/ref/jdsl.groovy
ENV CASC_JENKINS_CONFIG /var/jenkins_conf

# Copies [first file, the local one] to [second path/file this place in the container]
COPY plugins.txt PLUGIN_DEST
# COPY jdsl.groovy JOBDSL_DEST
COPY jenkins.yaml /var/jenkins_conf/jenkins.yaml
# Install Plugins
RUN /usr/local/bin/install-plugins.sh < PLUGIN_DEST
mvn clean package
echo "Enter Docker Hub username"
read username
docker build -t $username/autoci-jenkins-svlt:latest $(dirname $0)
docker push $username/autoci-jenkins-svlt:latest
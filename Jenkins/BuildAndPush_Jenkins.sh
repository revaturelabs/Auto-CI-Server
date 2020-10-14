echo "Enter Docker Hub username"
read username
docker build -t $username/autoci-jenkins:latest $(dirname $0)
docker push $username/autoci-jenkins:latest
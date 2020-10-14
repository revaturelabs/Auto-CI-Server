mvn clean package
docker build -t autoci-jenkins-svlt:latest $(dirname $0)
docker tag autoci-jenkins-svlt:latest 855430746673.dkr.ecr.us-east-1.amazonaws.com/autoci-jenkins-svlt:latest
docker push 855430746673.dkr.ecr.us-east-1.amazonaws.com/autoci-jenkins-svlt:latest
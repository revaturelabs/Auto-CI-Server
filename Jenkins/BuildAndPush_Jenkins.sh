docker build -t autoci-jenkins .
docker tag autoci-jenkins:latest 855430746673.dkr.ecr.us-east-1.amazonaws.com/autoci-jenkins:latest
docker push 855430746673.dkr.ecr.us-east-1.amazonaws.com/autoci-jenkins:latest
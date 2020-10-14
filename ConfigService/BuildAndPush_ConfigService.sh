mvn clean package
docker build -t autoci-config .
docker tag autoci-config:latest 855430746673.dkr.ecr.us-east-1.amazonaws.com/autoci-config:latest
docker push 855430746673.dkr.ecr.us-east-1.amazonaws.com/autoci-config:latest
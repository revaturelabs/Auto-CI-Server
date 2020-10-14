mvn clean package
docker build -t autoci-azure .
docker tag autoci-azure:latest 855430746673.dkr.ecr.us-east-1.amazonaws.com/autoci-azure:latest
docker push 855430746673.dkr.ecr.us-east-1.amazonaws.com/autoci-azure:latest
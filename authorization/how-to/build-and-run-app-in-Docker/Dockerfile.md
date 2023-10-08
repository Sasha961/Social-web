locally:
    docker build -t auth-micro-service .
    docker run -p 8084:8084 --name auth auth-micro-service
    docker login
    docker tag auth-micro-service temo4kalebedev/auth-micro-service:0.0.10
    docker push temo4kalebedev/auth-micro-service:0.0.10
    
    
on server:
    sudo docker run -d -p 8084:8084 --restart unless-stopped temo4kalebedev/auth-micro-service:0.0.10

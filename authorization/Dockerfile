# Install Docker Images for OpenJDK Version 11 binaries built by AdoptOpenJDK. https://hub.docker.com/r/adoptopenjdk/openjdk11
FROM adoptopenjdk/openjdk11:alpine-jre

# Specify a label. For example, image developer, etc. Optional item.
LABEL artifact="auth-micro-service"
LABEL version="0.0.7-SNAPSHOT"
LABEL maintainer="Artem Lebedev"
LABEL email="artlebedev2006@gmail.com"

# Specify a mount point for external data inside the container (as we remember, this is Linux)
VOLUME /app/backend
# The external port on which our application will be accessed from the outside
EXPOSE 8084

# Specify where the *.jar file is located in our application
ARG JAR_FILE=target/auth-micro-service-0.0.7-SNAPSHOT.jar

# Add *.jar file to the image under the name auth-micro-service.jar
ADD ${JAR_FILE} auth-micro-service.jar

# JAR run command
ENTRYPOINT ["java","-jar","auth-micro-service.jar"]

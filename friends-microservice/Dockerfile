FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/friends-microservice-0.0.1-SNAPSHOT.jar
WORKDIR /opt/friends-ms
COPY ${JAR_FILE} friends-ms.jar
EXPOSE 8083
CMD ["java", "-jar", "friends-ms.jar"]
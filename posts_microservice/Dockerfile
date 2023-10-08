FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/Microservice-Posts-0.0.1-SNAPSHOT.jar
WORKDIR /opt/buildagent/work
COPY ${JAR_FILE} posts_microservice.jar
EXPOSE 8089
CMD ["java", "-jar", "posts_microservice.jar"]
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
WORKDIR /gateway/gateway-server
COPY ${JAR_FILE} gateway-server.jar
EXPOSE 8088
CMD ["java", "-jar", "gateway-server.jar"]
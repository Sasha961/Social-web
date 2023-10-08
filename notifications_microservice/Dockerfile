FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=/target/notifications-0.0.2-SNAPSHOT.jar
WORKDIR /opt/buildagent/work
EXPOSE 8086
COPY ${JAR_FILE} notifications.jar
CMD ["java","-jar", "notifications.jar"]

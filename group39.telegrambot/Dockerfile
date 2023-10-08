FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/group39.telegrambot-0.0.1-SNAPSHOT.jar
WORKDIR /opt/telegrambot
COPY ${JAR_FILE} telegrambot.jar
EXPOSE 8097
CMD ["java", "-jar", "telegrambot.jar"]
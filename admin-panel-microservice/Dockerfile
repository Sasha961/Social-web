FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
WORKDIR /opt/admin-ms
COPY ${JAR_FILE} admin-ms.jar
EXPOSE 8083
CMD ["java", "-jar", "admin-ms.jar"]
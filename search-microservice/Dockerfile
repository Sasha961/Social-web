FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/search-microsevice-0.0.1-SNAPSHOT.jar
WORKDIR /opt/search-ms
COPY ${JAR_FILE} search-ms.jar
EXPOSE 8092
CMD ["java", "-jar", "search-ms.jar"]
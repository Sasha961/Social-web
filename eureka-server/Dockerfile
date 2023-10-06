FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
WORKDIR /eureka/eureka-server-1
COPY ${JAR_FILE} eureka-serveк-1.jar
EXPOSE 9001
CMD ["java", "-jar", "eureka-server-1.jar", "--spring.profiles.active=eureka-1"]

FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
WORKDIR /eureka/eureka-server-2
COPY ${JAR_FILE} eureka-server-2.jar
EXPOSE 9002
CMD ["java", "-jar", "eureka-server-2.jar", "--spring.profiles.active=eureka-2"]

FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
WORKDIR /eureka/eureka-server-3
COPY ${JAR_FILE} eureka-server-3.jar
EXPOSE 9003
CMD ["java", "-jar", "eureka-server-3.jar", "--spring.profiles.active=eureka-3"]
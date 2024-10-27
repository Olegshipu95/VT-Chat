FROM maven:4.0.0-jdk-21-alpine as builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM java:21

COPY --from=builder /usr/src/app/target/chat-core-all.jar /usr/app/chat-core-all.jar

ENTRYPOINT ["java", "-jar", "/usr/app/chat-core-all.jar"]
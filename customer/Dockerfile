FROM maven:latest as builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:21

COPY --from=builder /usr/src/app/target/customer-all.jar /usr/app/customer-all.jar

ENTRYPOINT ["java", "-jar", "/usr/app/customer-all.jar"]

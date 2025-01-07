FROM gradle:8.8-jdk17 as build

WORKDIR /workspace

COPY src ./src
COPY build.gradle ./build.gradle
COPY settings.gradle ./settings.gradle

RUN gradle clean build -x test

FROM amazoncorretto:17-alpine

WORKDIR /app

COPY --from=build /workspace/build/libs/sports-user-0.0.1-SNAPSHOT.jar ./sports-user.jar

ENTRYPOINT ["java", "-jar", "sports-user.jar"]
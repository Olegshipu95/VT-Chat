FROM openjdk:21

ARG SRC=/src

COPY . ${SRC}

WORKDIR ${SRC}

RUN ./gradlew --no-daemon -x test clean build

ENTRYPOINT ["java","-jar","build/libs/chat-core-all.jar"]

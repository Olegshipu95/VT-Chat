FROM openjdk:21

ARG SRC=/src

COPY . ${SRC}

WORKDIR ${SRC}

RUN mvn clean package -DskipTests

ENTRYPOINT ["java","-jar","target/chat-core-all.jar"]

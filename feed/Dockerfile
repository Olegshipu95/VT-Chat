FROM maven:latest as builder

WORKDIR /app

# Копируем файлы проекта (pom.xml и исходники)
COPY pom.xml .
COPY src ./src

# Выполняем сборку проекта (можно добавить параметры сборки)
RUN mvn clean package

FROM openjdk:21

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем jar-файл из предыдущего этапа
COPY --from=builder /app/target/*.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
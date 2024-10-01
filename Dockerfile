# Сборка проекта с использованием Maven
# Используем официальный образ OpenJDK 22
FROM openjdk:22-jdk-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

COPY target/lib /app/lib
# Копируем собранный JAR файл в контейнер
COPY target/WishListBackend-0.0.1-SNAPSHOT.jar /app/app.jar

# Определяем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
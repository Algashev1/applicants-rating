# Этап сборки
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# УСТАНАВЛИВАЕМ MAVEN
RUN apt-get update && apt-get install -y maven

COPY . .
RUN mvn clean package -DskipTests

# Этап запуска
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]

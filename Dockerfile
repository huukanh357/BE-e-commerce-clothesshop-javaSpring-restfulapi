FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /appBE

COPY pom.xml ./
COPY src ./src

RUN mvn -B -DskipTests clean package

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /appBE/target/*.jar app.jar

RUN mkdir -p /app/uploads/products /app/uploads/avatars

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

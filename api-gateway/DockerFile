# ---------------------------
# STAGE 1 - Build
# ---------------------------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ---------------------------
# STAGE 2 - Runtime
# ---------------------------
FROM eclipse-temurin:17

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

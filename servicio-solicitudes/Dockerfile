FROM eclipse-temurin:21
WORKDIR /app
COPY target/servicio-solicitudes-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
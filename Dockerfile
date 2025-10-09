# --- BUILD STAGE ---
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# --- RUN STAGE ---
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy file JAR đã build từ stage trước
COPY --from=build /app/target/tkd_v1-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]


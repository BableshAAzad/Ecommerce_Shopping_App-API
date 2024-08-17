# First stage: Build the application
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Second stage: Run the application
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/ecommerce-shopping-app.jar ecommerce-shopping-app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "ecommerce-shopping-app.jar"]
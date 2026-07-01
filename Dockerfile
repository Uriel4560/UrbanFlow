FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw && ./mvnw -q -DskipTests package

EXPOSE 8080

CMD ["java", "-jar", "target/urbanflow-1.0.0-SNAPSHOT.jar"]
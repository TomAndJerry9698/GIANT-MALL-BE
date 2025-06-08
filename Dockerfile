# Stage 1: 빌드
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

# Stage 2: 실행
FROM amazoncorretto:17 AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/giant-mall-0.0.1-SNAPSHOT.jar /app/giant-mall-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/app/giant-mall-0.0.1-SNAPSHOT.jar"]
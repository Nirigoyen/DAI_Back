# Stage 1: Build the application
FROM 3.9.7-eclipse-temurin-11 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Environment variables
ARG DB_URL
ARG DB_USER
ARG DB_PASSWORD
ARG OBS_URL
ARG BUCKET_NAME
ARG TMDB_TOKEN

ENV SPRING_DATASOURCE_URL=$DB_URL
ENV SPRING_DATASOURCE_USERNAME=$DB_USER
ENV SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD
ENV OBS_URL=$OBS_URL
ENV BUCKET_NAME=$BUCKET_NAME
ENV TMDB_TOKEN=$TMDB_TOKEN

# Expose port
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
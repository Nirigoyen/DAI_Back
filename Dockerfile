# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

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
ARG PEPPER
ARG OBS_ENDPOINT

ENV SPRING_DATASOURCE_URL=$DB_URL
ENV SPRING_DATASOURCE_USERNAME=$DB_USER
ENV SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD
ENV OBS_URL=$OBS_URL
ENV BUCKET_NAME=$BUCKET_NAME
ENV TMDB_TOKEN=$TMDB_TOKEN
ENV SPRING_PROFILES_ACTIVE=dev
ENV PEPPER=$PEPPER
ENV OBS_ENDPOINT=$OBS_ENDPOINT

# Expose port
EXPOSE 8080
EXPOSE 8443


# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
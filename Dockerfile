FROM maven:3.9.5-eclipse-temurin-21 AS build


WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline


COPY src /app/src


RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-jammy

RUN apt-get update && apt-get install -y ca-certificates && apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app
EXPOSE 8083

COPY --from=build /app/target/*.jar app.jar


ENTRYPOINT ["java","-jar","/app/app.jar"]

FROM gradle:jdk17-corretto-al2023 AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/

COPY src /app/src

RUN gradle clean
RUN gradle build

FROM eclipse-temurin:17-jre-alpine

RUN apk add --no-cache bash

WORKDIR /app

COPY --from=build /app/build/libs/cuenta-bancaria-1.0.0.jar /app/CuentaBancaria.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "CuentaBancaria.jar"]
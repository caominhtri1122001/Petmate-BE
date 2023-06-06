#
# Build stage
#
FROM maven:3.8.5-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests


#
# Package stage
#
FROM adoptopenjdk/openjdk15:alpine-jre
COPY --from=build /target/api.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
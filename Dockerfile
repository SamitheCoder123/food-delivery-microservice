FROM openjdk:17-jdk-alpine
EXPOSE 8013
ADD target/restaurant-0.0.1-SNAPSHOT.jar restaurant-service.jar
ENTRYPOINT ["java","-jar","/restaurant-service.jar"]

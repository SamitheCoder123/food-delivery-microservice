FROM openjdk:17-jdk-alpine
EXPOSE 8012
ADD target/location-0.0.1-SNAPSHOT.jar location-service.jar
ENTRYPOINT ["java","-jar","/location-service.jar"]

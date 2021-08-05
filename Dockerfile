FROM openjdk:8-jdk-alpine
COPY target/beer-finder-0.0.1-SNAPSHOT.jar beer-finder-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/beer-finder-0.0.1-SNAPSHOT.jar"]

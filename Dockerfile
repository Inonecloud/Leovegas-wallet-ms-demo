FROM openjdk:11
ADD target/Leovegas-wallet-ms-demo-0.0.1-SNAPSHOT.jar wallet-misroservice.jar
EXPOSE 8080 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=DEV", "-jar", "/wallet-misroservice.jar"]
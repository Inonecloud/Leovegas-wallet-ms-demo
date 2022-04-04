FROM openjdk:11
ADD target/Leovegas-wallet-ms-demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
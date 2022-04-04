
# Test assigment for LeoVegas

This application runs on port 8080 by default and host http://localhost 


## How to start this application
This has stubbed players list which works with spring profile 'DEV'.
There are multiple ways how to run this app

### Via command line by java -jar 
To run this application you should execute following commands <br>
`mvn clean install -Dspring.profiles.active=DEV`<br>
`java -jar -Dspring.profiles.active=DEV ./target/Leovegas-wallet-ms-demo-0.0.1-SNAPSHOT.jar`

### Via command line by maven
`mvn spring-boot:run -Dspring-boot.run.profiles=DEV`

### Via Docker
`mvn clean install -Dspring.profiles.active=DEV`<br>
`docker build -t leovegas/wallet-microservice .`<br>
`docker run -p 8080:8080 leovegas/wallet-microservice -e "SPRING_PROFILES_ACTIVE=DEV" --name leovegas-wallet-ms:latest`

## Database
This app uses H2 in memory database and migrations engine Flyway. 
To provide persist data across restarts H2 works in File mode. Database file is located by path `src/main/resources/data/`
If you want to delete data please delete all files in this folder.

FlayWay creates tables and initialize start up data on the first application run. 
Flyway migration files is located by following path 
`src/main/resources/db/migration`

### OpenApi documentation
When application is running you can watch and test REST API via Swagger 
which available by following link
http://localhost:8080/swagger-ui/
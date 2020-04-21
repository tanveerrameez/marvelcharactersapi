Project Title
Please see REST Marvel API.docx for instructions

Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 
This is a spring Boot project

Prerequisites

The following environment variables/values should be set in the system where the application will be running:
MARVEL_PRIVATE_KEY =  private key of the Marvel developer account 
MARVEL_PUBLIC_KEY = public key of the Marvel developer account 
GOOGLE_API_KEY = the google api key  

The application requires this in form of environment variables. You can use your keys, or request the author the keys.
The keys are not provided with the code for security reasons.

Installing

1. mvn clean install (to skip test use mvn clean install -DskipTests)
Please note that unit tests might take a long time, since the character data is loaded during instantiation of the service class,
and the number of characters is nearly 1500. moreover the class is instantiated several times during the unit testing. 
The entire unit test can take about 10-15 minutes ! I did not get time to optimise the tests performance.

2. cd target
3. java -jar yapily-0.0.1-SNAPSHOT.jar
4. Once the application starts, the Swagger documentation is available in : http://localhost:8080/swagger-ui.html#

5. Use Postman (wait for about 2-3 minutes for the application to start and load the initial data)
  a.  GET: http://localhost:8080/marvel/characters/
  b.  GET: http://localhost:8080/marvel/characters/1009718
  c.  GET: http://localhost:8080/marvel/characters/1009718? language=fr


Running the tests
The junit tests are in the src/test/java folder

Built With
Maven - Spring Boot


Authors
Tanveer Rameez Ali
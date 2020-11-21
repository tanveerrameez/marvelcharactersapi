# Project 
The Marvel Comics API allows developers to access information about Marvel's vast library of
comics.
We want you to use the Marvel API (see http://developer.marvel.com/) to build a Characters API:
Part One:
1. Serve an endpoint /characters that returns all the Marvel character ids only, in a JSON
array of numbers.
a. Because Marvel API only returns max 100 records per request, you need to load all of
them beforehand with your application, and cache it in memory or file, to efficiently
serve your endpoint;
b. The request should be something like:
2. Serve an endpoint /characters/{characterId} that contains the real-time data from the
Marvel API /v1/public/characters/{characterId}, but containing only the following
information about each character: id, name, description, thumbnail
You'll need to sign up for Marvel developer API key at http://developer.marvel.com (free)
Once you have a key then the API documentation is at http://developer.marvel.com/docs
": 1009718,
, 1009144, 1010699, 1016823, 1009148, 1011334, … ]
Part Two:
Lets add more value to the Characters API.
3. Enable a translated version to another language of the character’s “description”.
a. Accept a query parameter with the language ISO-639-1 code: /characters/
{characterId}?language={languageCode}
b. Use any translation service of your choice, it can be an API or library, but the goal is to
execute the translations in real-time
4. Create a Swagger specification for your Characters API that can be viewed with Swagger UI
or imported to Postman
Constraints
" We're looking for a running server that can be accessed on http://localhost:8080/
" Use of third party libraries is ok; in the coding interview we'll be asking you about your choices
" The API keys / secrets should not be stored with the code.
See here: https://support.google.com/cloud/answer/6310037
On Completion
Also write a markdown README.md with instructions on how to:
" Install any dependencies, files or environment variables your code requires


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

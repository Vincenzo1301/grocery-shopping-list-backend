# Grocery Shopping List Backend

This repository contains the code of the Grocery Shopping List backend.

## Motivation
This Spring Boot Application was created as part of a HOBBY project, named Grocery Shopping List. The aim of the project is to make a shopping list 
based on the contents of your refrigerator in a simple and quick way. The backend is responsible for storing the data about the food and its availability and to 
make them available via REST interface. This interface is then used by the frontend application.

## Getting Started
What do you need to get this code up and running on your own system?

### Requirements
For building and running the application you need:
* JDK 1.8
* Maven 2

### Running the application locally
There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the de.jambit.maluku.MalukuBackendApplication class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like. Run the following command in the directory of the project:

> mvn spring-boot:run

## Swagger UI
Suitable documentation for the API is Swagger UI 2. In order to be able to open the UI locally, the application must run locally and in a browser must be navigated to the following page:

> http://localhost:8080/swagger-ui.html

Learn more: https://swagger.io/tools/swagger-ui/

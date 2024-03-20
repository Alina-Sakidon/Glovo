# Spring Boot "Glovo" Project

## Description

This is a simple Java / Maven / Spring Boot application that can be used as a starter for creating a microservice.

## Profiles
* active profile "default" with PostgresSQL relational database
* testing profile "test" with an in-memory database (H2) 

## Running the project with PostgresSQL

This project uses PostgresSQL database so that you need to install a database in order to run it.
You can install PostgresSQL server by one of these methods:
* manually. For more, see https://www.postgresql.org/docs/current/runtime.html
* using Docker. For more, see https://hub.docker.com/_/postgres 

## How to Run

* Clone this repository
* Make sure you are using JDK and Maven 
* You can build the project and run the tests by running **maven clean -> test**
* Once successfully built, you can run the service by one of these two methods:
```
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=default"
```
```
        using IDE:  maven clean -> compile -> run GlovoApplication.java
```
Once the application runs you should see something like this

```
2024-03-20 08:19:04.298  INFO 35788 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2024-03-20 08:19:04.351  INFO 35788 --- [           main] com.company.glovo.GlovoApplication       : Started GlovoApplication in 5.961 seconds (JVM running for 7.129)
```

## To view your H2 in-memory database
The 'test' profile runs on H2 in-memory database. To view and query the database you can browse to http://localhost:8081/h2-console. Default username is 'sa' with a password 'password'.
Note: integration tests use random port generation.

## Here are some API endpoints you can call:
### Create an order resource

```
curl --location 'http://localhost:8080/api/v1/orders' \
--header 'Content-Type: application/json' \
--data '{
    "cost": 15.1,
    "date": "2015-01-02",
    "products" : [
        {
            "name" : "product1",
            "cost" : 433.4
        },
        {
            "name" : "product2",
            "cost" : 533.4
        }
    ]
}'
```
### Get all order resources
```
curl --location 'http://localhost:8080/api/v1/orders' \
--header 'Content-Type: application/json' \
```

### Get created order resource
```
curl --location 'http://localhost:8080/api/v1/orders/1' \
--header 'Content-Type: application/json' \
```
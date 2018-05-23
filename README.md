# Spring boot, Spring security, Spring Data JPA User Involvement app project

## About

Sample app generated from spring boot initializr: https://start.spring.io/
Used to build project structure!

## Technical Stack

- Java 1.8
- Spring boot maven plugin
- Spring boot 2.0.2
- Postgresql 42.2.2
- H2 - for tests
- Lombok abstraction 1.16.20
- JPA with Postgresql/H2 for explanation
- REST API model validation 
- Spring Boot starter test - junit and integration test

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the com.github.stodorov.UserInvolvementApplication class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:
mvn spring-boot:run

Test on postman or another API development environment /I test with extention of Chrome - Advanced REST client/:
-------------------
## Register new User:
POST - http://localhost:8080/api/register
{
  "username": "stodorov",
  "password": "pass"
}


## User login:
POST - http://localhost:8080/api/login
{
  "username": "stodorov",
  "password": "pass"
}

## Post new Announcement:
POST - http://localhost:8080/api/announcement
{
	"title": "fb add blockchain",
	"content": "fb think about to add blockchain technology"
}

## Like posted Announcement based on announcemenId:
POST - http://localhost:8080/api/announcements/{announcementId}/like

## Retrieve all Users Feedback to announcement by announcementId:
GET - http://localhost:8080/api/announcement/{announcementId}



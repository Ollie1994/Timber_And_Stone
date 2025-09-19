# TIMBER + STONE

### Description
This is a demo-project created by a group of three Swedish students to learn Java and the Spring framework. This API is inspired by airbnb.com but angled towards nature-friendly and off-grid rentals.
After the MVP had been created, we put the main focus on object oriented design principles and patterns. To dig deeper into this we decided to refactor our classes for Bookings and Rentals. 

### Build & Run
#### Prerequisities: Install Java and Maven on your local machine and setup a MongoDB database. 
- Clone the Git-repository locally to your machine.
- Create an application.yaml file (at src\main\resources\application.yaml) to store the following global variables:
```
spring:
  data:
    mongodb:
      uri: your-mongo-db-URL

jwt:
  secret: your-JWT-Secret
  expirationMs: your-JWT-expiration-in-ms
```
- Checkout the Dev-branch.
- Run the program.
- You can use the following documentation to test the endpoints in PostMan: https://documenter.getpostman.com/view/40893378/2sAYXFjdax

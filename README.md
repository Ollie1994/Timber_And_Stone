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
- Checkout to the Dev-branch.
- Run the program.
- You can use the following documentation to test the endpoints in PostMan: https://documenter.getpostman.com/view/40893378/2sAYXFjdax

### Design Patterns & Principles
We have designed our project with the principles DRY, KISS, Open-Closed and Single Responsibility to ensure the code is easy to interpret, avoid duplication and keeping the code logically separated. 
The patterns we have implemented are:
- Chain of Responsibility for POST-Booking validation
- Chain of Responsibility for POST-Rental validation
- Command Pattern for rentals.

These implementations promotes maintainability and scaleability for future improvements and changes.

### UML
#### CLASSDIAGRAM
<img width="3660" height="4742" alt="OOAD Grupp Uppgift V 2 (1)" src="https://github.com/user-attachments/assets/52a79a4b-0f52-4c15-80e7-1117c7be597a" />

#### USE CASES
<img width="4070" height="3274" alt="OOAD Grupp Uppgift V 2 (2)" src="https://github.com/user-attachments/assets/b7e897f1-2ddf-4233-89f3-3200c6cb2678" />

#### ACTIVITYDIAGRAM
<img width="7447" height="3791" alt="OOAD Grupp Uppgift V 2 (3)" src="https://github.com/user-attachments/assets/7772c734-5bf1-40de-9b32-62a0bd1f252d" />

#### SEQUENCEDIAGRAM
<img width="6965" height="5226" alt="OOAD Grupp Uppgift V 2 (4)" src="https://github.com/user-attachments/assets/d6fae5b1-0100-46cf-ac27-f629ae4dcdda" />

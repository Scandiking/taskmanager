# Arbeidskrav 2/2: Spring Boot REST API Development

---

This is an assignment done in order to qualify for the exam in application development APP2000 at University of South-Eastern Norway at campus Ringerike in Hønefoss.

---

## Table of Contents
<!-- TOC -->
* [Arbeidskrav 2/2: Spring Boot REST API Development](#arbeidskrav-22-spring-boot-rest-api-development)
  * [Table of Contents](#table-of-contents)
  * [Objective](#objective)
  * [Instructions](#instructions)
    * [1. Develop REST API endpoints](#1-develop-rest-api-endpoints)
    * [2. Required components](#2-required-components)
    * [3. Implementation requirements](#3-implementation-requirements)
    * [4. Technical Documentation](#4-technical-documentation)
    * [5. Submission Guidelines](#5-submission-guidelines)
  * [Endpoints](#endpoints)
    * [Endpoint 1: Rooms](#endpoint-1-rooms)
      * [GET](#get)
      * [POST](#post)
      * [PUT](#put)
      * [DELETE](#delete)
    * [Endpoint 2: Persons](#endpoint-2-persons)
      * [GET](#get-1)
        * [Get all persons](#get-all-persons)
        * [Get a specific person](#get-a-specific-person)
      * [POST](#post-1)
      * [PUT](#put-1)
      * [DELETE](#delete-1)
    * [Endpoint 3: Tasks](#endpoint-3-tasks)
  * [Setup instructions for running the API locally](#setup-instructions-for-running-the-api-locally)
  * [API documentation](#api-documentation)
<!-- TOC -->

---

## Objective

This assignment ensures that each team member develops fundamental skills in building REST APIs using Spring Boot by creating individual endpoints and implementing basic CRUD operations.

---

## Instructions

<details>

<summary>
Click to expand...
</summary>

---



---


### 1. Develop REST API endpoints
  - Each team member should design and implement 3-4 REST endpoints using Spring Boot.
  - The API should include a mix of HTTP methods (GET, POST, PUT, DELETE).
  - Implementation should follow the Spring Boot architecture patterns with proper separation of concerns.
### 2. Required components
  - Create 2-3 entity classes that represent your data models.
  - Implement at least one service class with business logic. 
  - Create 1-2 repository interfaces for data access.
  - Develop one controller class to handle HTTP requests.
  - Include proper exception handling and response status codes.
### 3. Implementation requirements
  - Use appropriate annotations (@RestController, @Service, @Repository, etc.)
  - Implement proper request/response DTOs where necessary
  - Include input validation using annotations (@Valid, @NotNull, etc.)
  - Document your API endpoints using Swagger/OpenAPI annotations
  - Follow RESTful naming conventions and best practices.

### 4. Technical Documentation
  - Provide documentation for each endpoint including:
    - HTTP method and URL path
    - Request/response formats with examples
    - Required parameters and their constraints
    - Possible response status codes
  - Include setup instructions for running the API locally
### 5. Submission Guidelines
  - Submit your Spring Boot project as a Git repository
  - Include a README.md with:
    - Project setup instructions
    - API documentation
    - Examples of API usage
  - Each team member should submit their individual implementation

__Note:__ While the implementations are individual, ensure your API design aligns with the team's project domain and could potentially be integrated into the main project in the future.

</details>

---

## Endpoints

<CITE>«Each team member should design and implement 3-4 REST endpoints using Spring Boot»</CITE>

Endpoint 1: Rooms ([localhost:3000/api/rooms]("api/rooms"))
- Get all rooms `GET`
- Get room by ID `GET`
- Create room `POST`
- Update room `PUT`
- Delete room `DELETE`

Endpoint 2: Persons ([localhost:3000/api/persons]("api/persons"))
- Get all persons `GET`
- Get person by ID `GET`
- Create person `POST`
- Update person `PUT`
- Delete person `DELETE`

Endpoint 3: Tasks ([localhost:3000/api/tasks]("api/tasks"))
- Get all tasks `GET`
- Get task by ID `GET`
- Create task `POST`
- Update task `PUT`
- Delete task `DELETE`

OK. You might have noticed that you can not create a task using only a task `id`, task `title` and optionally task `description`. This gives a `400 Bad Request` error. This is to expect. Who created the task? Who is the task for? Is it completed? We can not know tell if we only `POST` the afforementioned fields. We will need some fields from other tables (`Person`, `Room`) as well. You might have gotten a long String of errors. These are the important parts now:

```error
default message [roomId]]; 
default message [Room ID is required]] [Field error in object 'taskDTO' on field 'name': rejected value [null];
default message [name]]; 
default message [Task name is required]] [Field error in object 'taskDTO' on field 'creatorId': rejected value [null]; 
default message [creatorId]]; 
default message [Creator ID is required]] ]

```

This tells us while `POST`-ing a task we need all the following fields:
```json
{
  "name": "The name of the task",
  "description": "The description of the task which is optional and why you don't see it in the error message",
  "roomId": 1,
  "creatorId": 1,
  "assigneeIds": [1] 
}
```
Make sure to have the square brackets around the `assigneeIds` field. This is because it is an array of integers. If you only have one assignee, you can still use the square brackets.

---

### Endpoint 1: Rooms

#### GET
To retrieve (`GET`) all rooms there is, you can simply go to `localhost:3000/api/rooms` in your browser. It should return `200 OK` and `[]` if there are no rooms in the database. If there are rooms in the database, it should return `200 OK` and a list of rooms. If you want to get a specific room, take note of the room ID and go to `localhost:3000/api/rooms/{id}` in your browser. Replace `{id}` with the room ID you took note of. It should return `200 OK` and the room you wanted to get.
#### POST

`localhost:3000/api/rooms` should initially show `[]` if you are using your browser and have no data inserted (`POST`-ed). This is because there are no records of rooms in the database. Let us populate it by creating a room using the `POST` method in Postman. In the input field, put:

```json
{
  "name": "Room 1",
  "capacity": 10
}
```
...and hit send. It should say `201 Created` if everything went as expected. To check whether the record was created, we can use the `GET` method to get all rooms. It should return `200 OK` and the following:

```json
{
  "id": 1,
  "name": "Room 1",
  "description": null,
  "capacity": 10
}
```

#### PUT

If it did not go as expected you likely saw a `400 Bad Request` or `500 Internal Server Error`. This is because the input was not valid or the server encountered an error. That is for later. Let us focus on using the `PUT` method since there is a _description_ field that we did not care for in the last record. We can update the record by using the `PUT` method and the following input:

```json
{
  "id": 1,
  "name": "Room 1",
  "description": "This is a room meant for cooking",
  "capacity": 10
}
```

Did it work? No? Does it say `405 Method Not Allowed`? This is because we want to `PUT` a specific room so `localhost:8080/api/rooms` won't cut it. We need to be a little more specific. Try `localhost:8080/api/rooms/1` instead. It should return `200 OK` and then you can `POST` the following:

```json
{
  "id": 1,
  "name": "Room 1",
  "description": "This is a room meant for cooking but you can also eat here",
  "capacity": 10
}
```
As long as as the RoomController.java have a `@PutMapping` annotation with the value `"/{id}"` after `/rooms` one need to specify _the_ room you want to `PUT` or `DELETE`. This is not needed for `GET` or `POST` since the API fixes this itself automatically. If you want to delete the record, you can use the `DELETE` method and `localhost:8080/api/rooms/1` as the URL. It should return `200 OK` if everything went as expected.

#### DELETE

To delete a given room, we need to know its room ID. We already know that the room ID for Room 1 is 1. We can use the `DELETE` method and `localhost:8080/api/rooms/1` as the URL. It should return `200 OK` if everything went as expected. If you want to check if the record was deleted, you can use the `GET` method to get all rooms. It should return `200 OK` and `[]` if everything went as expected.

---

So, in order to recap:
To

1. Get all rooms

    - You just need to put localhost:3000/api/rooms in your browser
   
2. Get a specific room

    - You need to put localhost:3000/api/rooms/{id} in your browser. Replace {id} with the id of the room you want to get.
   
3. Create a room
    - You need to use the `POST` method in Postman with the following input:
        ```json
        {
          "name": "Room 1",
          "description": "This is a room",
          "capacity": 10
        }
        ```
   
4. Update a room
    - You need to use the `PUT` method in Postman with the following input:
      ```json
      {
        "id": 1,
        "name": "Room 1",
        "description": "This is a new description of the room and thus what we PUT into the new record.",
        "capacity": 10
      }
      ```

5. Delete a room
    - You need to use the `DELETE` method in Postman with the URL `localhost:3000/api/rooms/{id}`. Replace {id} with the id of the room you want to delete.

---

### Endpoint 2: Persons

#### GET

##### Get all persons
- You just need to put `localhost:3000/api/persons` in your browser
##### Get a specific person
- You need to put `localhost:3000/api/persons/{id}` in your browser. Replace `{id}` with the ID of the person you want to get. If you don't know the ID, you can use the `GET` method to get all persons and find the ID there.


#### POST

- You need to use the `POST` method in Postman with the following input:
    
    ```json
    {
        "firstName": "John",
        "lastName": "Doe",
        "email": "address@emailcompany.com",
        "phone":"047 12345"
    }
    ```
   
  Do not worry about the primary key. It will be generated automatically. In fact, if you try to specify one, you will get a `400 Bad Request` error. This is because the primary key is generated automatically and should not be specified by the user. If you want to check whether the record was created, you can use the `GET` method to get all persons. It should return `200 OK` and the following:
    
    ```json
    {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "address@company.com",
        "phone":"047 12345"
  }
    ```

#### PUT

- You need to use the `PUT` method in Postman with the following input:
  ```json
  {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "address@newemailcompany.com",
      "phone":"047 12345"
  }
  ```
  In this case, we __do__ need the ID because the API needs to know what record to `PUT`.

#### DELETE

- You need to use the `DELETE` method in Postman with the URL `localhost:3000/api/persons/{id}`. Replace {id} with the id of the person you want to delete.
  ```json
  {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "address@newemailcompany.com",
      "phone":"047 12345"
  }
  ```

---

### Endpoint 3: Tasks

1. Get all tasks
    - You just need to put localhost:3000/api/tasks in your browser

2. Get a specific task
    - You need to put localhost:3000/api/tasks/{id} in your browser. Replace {id} with the id of the task you want to get. If you don't know the id, you can use the `GET` method to get all tasks and find the id there.

3. Create a task
    - You need to use the `POST` method in Postman with the following input:
      ```json
      {
        "title": "Task 1",
        "description": "This is a task",
        "status": "Not started"
      }
      ```
   
      Do not worry about the primary key. It will be generated automatically.

4. Update a task
    - You need to use the `PUT` method in Postman with the following input:
      ```json
      {
        "id": 1,
        "title": "Task 1",
        "description": "This is a new description of the task and thus what we PUT into the new record.",
        "status": "Not started"
      }
      ```
      
5. Delete a task
    - You need to use the `DELETE` method in Postman with the URL `localhost:3000/api/tasks/{id}`. Replace {id} with the id of the task you want to delete.

---

## Setup instructions for running the API locally
0. Install [PostgreSQL](https://www.postgresql.org/download/) and [Postman](https://www.postman.com/downloads/) (or curl) and [IntelliJ IDEA](https://www.jetbrains.com/idea/) (or the IDE of your choice that supports Java) and a good browser, for example [Librewolf](https://librewolf.net/) or [GNU IceCat](https://icecatbrowser.org/index.html) if you don't already have them. They might not be the [GOAT](https://www.dictionary.com/e/slang/g-o-a-t/), but they are [FOSS](https://en.wikipedia.org/wiki/The_Free_Software_Definition) (MPL-2.0/GPL-3.0-or-later).
- [ ] PostgreSQL
- [ ] Postman
- [ ] IDE with Java support
- [ ] Browser
1. Clone the repository from [GitHub]()
2. Open the project in your IDE
3. Open PostgreSQL and create a database called "taskmanager". Keep PostgreSQL running.
4. Run the project from your IDE that supports Java
5. Go to your browser and enter `localhost:3000/api/` to see if the project is running.
   - If it is not, make sure that the Postgres user is "postgres" and the password for the postgres user is "123". If it is not, you can change it in the `application.properties` file in the `src/main/resources` folder. You can also change the port from 3000 to something else if you want to. Just make sure that the port is not already in use.
6. Use Postman to test the endpoints by using the instructions on localhost:3000/api/.
7. Read this document to see what those endpoints are and how to use them
8. Enjoy!

---

## API documentation
- com.nag.taskmanager
  - Controller
    - ApiIndexController
      - Makes sure that the API is running. Also shows the available endpoints.
    - PersonController
      - Controls the Person endpoints
    - RoomController
      - Controls the Room endpoints
    - TaskController
      - Controls the Task endpoints
  - DTO
    - PersonDTO
      - Used for creating and updating persons not to expose the database
    - RoomDTO
      - Used for creating and updating rooms not to expose the database
    - TaskCreateDTO
      - Used for creating tasks not to expose the database
    - TaskDTO
      - Used for updating tasks not to expose the database
  - Model
    - Person
      - Works as a model for the Person entity in the database. Also used for creating and updating persons.
    - Room
      - Works as a model for the Room entity in the database. Also used for creating and updating rooms.
    - Task
      - Works as a model for the Task entity in the database. Also used for creating and updating tasks.
  - Repository
    - PersonRepository
      - Used for accessing the database.
    - RoomRepository
      - Used for accessing the database
    - TaskRepository
      - Used for accessing the database
  - Service
    - PersonService
      - Used for business logic
    - RoomService
      - Used for business logic
    - TaskService
      - Used for business logic
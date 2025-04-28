# Arbeidskrav 2/2: Spring Boot REST API Development


![Entities of this repository. Rooms, persons and tasks](src/main/resources/static/images/TaskManager.png)

---


This is an assignment done in order to qualify for the exam in application development APP2000 at University of South-Eastern Norway at campus Ringerike in Hønefoss. It is an application programming interface consisting of entities of rooms, persons and tasks that you can perform CRUD operations on by using your web browser (only for read) and/or Postman to create, read, update and delete.

---

## Table of Contents
<details>

<summary>
Click to expand...
</summary>

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
  * [Testing the API](#testing-the-api)
    * [Rooms](#rooms)
      * [GET](#get-2)
      * [POST](#post-2)
      * [PUT](#put-2)
      * [DELETE](#delete-2)
    * [Persons](#persons)
      * [GET](#get-3)
      * [POST](#post-3)
      * [PUT](#put-3)
      * [DELETE](#delete-3)
    * [Tasks](#tasks)
      * [GET](#get-4)
      * [POST](#post-4)
      * [PUT](#put-4)
      * [DELETE](#delete-4)
    * [PersonRooms](#personrooms)
      * [GET](#get-5)
      * [POST](#post-5)
      * [PUT](#put-5)
      * [DELETE](#delete-5)
    * [TaskPersons](#taskpersons)
      * [GET](#get-6)
      * [POST](#post-6)
      * [PUT](#put-6)
      * [DELETE](#delete-6)
  * [API documentation](#api-documentation)
    * [📁 com.nag.taskmanager](#-comnagtaskmanager)
      * [📦 Controllers](#-controllers)
      * [📦 DTO (Data Transfer Objects)](#-dto-data-transfer-objects)
      * [📦 Models](#-models)
      * [📦 Repositories](#-repositories)
      * [📦 Services](#-services)
<!-- TOC -->
</details>

---

## Objective

This assignment ensures that each team member develops fundamental skills in building REST APIs using Spring Boot by creating individual endpoints and implementing basic CRUD operations.

---

## Instructions

<details>

<summary>
Click to expand...
</summary>

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

This is a REST API with three endpoints: Rooms, Persons and Tasks. Each endpoint has the following methods:

Endpoint 1: Rooms ([localhost:8080/api/rooms]("api/rooms"))
- Get all rooms `GET`
- Get room by ID `GET`
- Create room `POST`
- Update room `PUT`
- Delete room `DELETE`

Endpoint 2: Persons ([localhost:8080/api/persons]("api/persons"))
- Get all persons `GET`
- Get person by ID `GET`
- Create person `POST`
- Update person `PUT`
- Delete person `DELETE`

Endpoint 3: Tasks ([localhost:8080/api/tasks]("api/tasks"))
- Get all tasks `GET`
- Get task by ID `GET`
- Create task `POST`
- Update task `PUT`
- Delete task `DELETE`

OK. You might have noticed that you cannot create a task using only a task `id`, task `title` and optionally task `description`. This gives a `400 Bad Request` error. This is to expect. Who created the task? Who is the task for? Is it completed? We can not know tell if we only `POST` the afforementioned fields. We will need some fields from other tables (`Person`, `Room`) as well. You might have gotten a long String of errors. These are the important parts now:

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
Make sure to have the square brackets around the `assigneeIds` field. This is because it is an array of integers. If you only have one assignee, you can still use the square brackets. If you have to or three assignees you write it like `[1, 2]` or `[1,2,3]`. Please notice that we __don't__ need an "id" field as this is autogenerated.

---

### Endpoint 1: Rooms

#### GET
To retrieve (`GET`) all rooms there is, you can simply go to `localhost:8080/api/rooms` in your browser. It should return `200 OK` and `[]` if there are no rooms in the database. If there are rooms in the database, it should return `200 OK` and a list of rooms. If you want to get a specific room, take note of the room ID and go to `localhost:8080/api/rooms/{id}` in your browser. Replace `{id}` with the room ID you took note of. It should return `200 OK` and the room you wanted to get.
#### POST

`localhost:8080/api/rooms` should initially show `[]` if you are using your browser and have no data inserted (`POST`-ed). This is because there are no records of rooms in the database. Let us populate it by creating a room using the `POST` method in Postman. In the input field, put:

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

If it did not go as expected, you likely saw a `400 Bad Request` or `500 Internal Server Error`. This is because the input was not valid or the server encountered an error. That is for later. Let us focus on using the `PUT` method since there is a _description_ field that we did not care for in the last record. We can update the record by using the `PUT` method and the following input:

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

    - You just need to put localhost:8080/api/rooms in your browser
   
2. Get a specific room

    - You need to put localhost:8080/api/rooms/{id} in your browser. Replace {id} with the id of the room you want to get.
   
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
    - You need to use the `PUT` method in Postman AND specify the roomID `localhost:8080/api/rooms/{roomIdYouWantToPut}` with the following input:
      ```json
      {
        "id": 1,
        "name": "Room 1",
        "description": "This is a new description of the room and thus what we PUT into the new record.",
        "capacity": 10
      }
      ```

5. Delete a room
    - You need to use the `DELETE` method in Postman with the URL `localhost:8080/api/rooms/{id}`. Replace {id} with the id of the room you want to delete.

---

### Endpoint 2: Persons

#### GET

##### Get all persons

- You need to put `localhost:80800/api/persons` in your browser

##### Get a specific person

- You need to put `localhost:8080/api/persons/{id}` in your browser. Replace `{id}` with the ID of the person you want to get. If you don't know the ID, you can use the `GET` method to get all persons and find the ID there.

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

- You need to use the `DELETE` method in Postman with the URL `localhost:8080/api/persons/{id}`. Replace {id} with the id of the person you want to delete.
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
    - You just need to put localhost:8080/api/tasks in your browser

2. Get a specific task
    - You need to put localhost:8080/api/tasks/{id} in your browser. Replace {id} with the id of the task you want to get. If you don't know the id, you can use the `GET` method to get all tasks and find the id there.

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
    - You need to use the `DELETE` method in Postman with the URL `localhost:8080/api/tasks/{id}`. Replace {id} with the id of the task you want to delete.

---

## Setup instructions for running the API locally

0. Install [PostgreSQL](https://www.postgresql.org/download/) and [Postman](https://www.postman.com/downloads/) (or curl) and [IntelliJ IDEA](https://www.jetbrains.com/idea/) (or the IDE of your choice that supports Java) and a good browser, for example [Librewolf](https://librewolf.net/) or [GNU IceCat](https://icecatbrowser.org/index.html) if you don't already have them. They might not be the [GOAT](https://www.dictionary.com/e/slang/g-o-a-t/), but they are [FOSS](https://en.wikipedia.org/wiki/The_Free_Software_Definition) (MPL-2.0/GPL-3.0-or-later).
- [ ] PostgreSQL
- [ ] Postman
- [ ] IDE with Java support
- [ ] Java
- [ ] Maven
- [ ] Internet browser

There are three ways to run the program.
### Downloaded zip, without IDE, using .bat-file
1. Unzip the file and locate the `Run_me_for_autosetup.bat` file.
2. Double-click the file to run it. This will automatically set up the database and run the project.

### Downloaded zip, without IDE, manually
1. Download the zip from Canvas.
2. Unzip it.
3. Open Bash and navigate to the unzipped folder.
4. Run the following command to build the project:
   ```bash
   mvn clean package
   ```
5. Run the following command to run the project:
   ```bash
    java -jar target/TaskManager-0.0.1-SNAPSHOT.jar
    ```
   (or whatever it says in terminal output)
   



### GitHub and IDE

1. Clone the repository from [GitHub]()
2. Open the project in your IDE
3. Open PostgreSQL and create a database called "taskmanager". Keep PostgreSQL running.
4. Run the project from your IDE that supports Java
5. Go to your browser and enter `localhost:8080/api` to see if the project is running.
   - If it is not, make sure that the Postgres user is "postgres" and the password for the postgres user is "123". If it is not, you can change it in the `application.properties` file in the `src/main/resources` folder. You can also change the port from 8080 to something else if you want to. Just make sure that the port is not already in use.
6. Use Postman to test the endpoints by using the instructions on localhost:8080/api/.
7. Read this document to see what those endpoints are and how to use them
8. Enjoy!

---

## Testing the API

Here are examples to copy and paste into Postman using the `taskmanager` database as it is after the setup instructions.
If you prefer or know how to, you can also import a started database by opening Bash and enter   
```Bash
psql -U {yourUsername} -a taskmanager < handy_taskmanager_database.sql
```

This way you won't have to manually add entries. If you _do_ want to add entries manually or did not get to import the database:

### Rooms

#### GET

Just go to `localhost:8080/api/rooms` in your browser. No need for Postman here.

#### POST

```json
{
  "name": "Room 1",
  "description": "This is a generic room",
  "capacity": 10
}
```

```json
{
  "name": "Dormitory dining hall",
  "description": "Dark academia styled dining hall",
  "capacity": 100
}
```

```json
{
  "name": "Ringerike Rådhus",
  "description": "The town hall of Ringerike",
  "capacity": 500
}
```

```json        
{
  "name": "Meløy kulturhus",
  "description": "Rural cultural house",
  "capacity": 550
}
```

You are not able to add multiple rooms at once from the API. 

#### PUT

Once again, take note of the ID you want to `PUT`. 
```json
{
  "id": 1,
  "name": "Room 1",
  "description": "This is a bigger room now so we will update the capacity to 20",
  "capacity": 20
}
```

#### DELETE

Take note of the ID you want to `DELETE`. Go to `localhost:8080/api/rooms/{id}` in Postman and use the `DELETE` method. When you are on say `localhost:8080/api/rooms/1`, you just need the ID in the raw text field in Postman like this:
```json
{
  "id": 1
}
```
and hit send. It should return `204 No Content` if everything went as expected.

### Persons

#### GET

Just go to `localhost:8080/api/persons` in your browser. No need for Postman here.

#### POST
In Postman, `POST` the following to `localhost:8080/api/persons`:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "mail@company.com",
  "phone":"047 12345"
}
```
Leave out any mentions of the ID. It will be generated automatically.

```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "email@anothercompany.com",
  "phone": "322 54321"
}
```

```json
{
  "firstName": "Jane",
  "lastName": "Austen",
  "email": "email2@electronicmailcompany.com",
  "phone": "987 65432"
}
```

```json
{
  "firstName": "Michael",
  "lastName": "Bolton",
  "email": "AllThatMatters@SoulProvider.com",
  "phone": "155 68732"
}
```

```json
{
  "firstName": "Avril",
  "lastName": "Lavigne",
  "email": "complicated@nobodyshome.com",
  "phone": "471 74114"
}
```

#### PUT
First, take note of the ID you want to `PUT`. Then, in Postman, use the `PUT` method and `localhost:8080/api/persons/{id}` as the URL. Replace `{id}` with the ID you took note of. In the input field, put:

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "mail@newcompany.com",
  "phone": "047 12345"
}
 ```
In this case, you need all fields present in the input field. If you leave out any of the fields, you will get a `400 Bad Request` error.

#### DELETE

Take note of the ID you want to `DELETE`. Go to `localhost:8080/api/persons/{id}` in Postman and use the `DELETE` method. When you are on say `localhost:8080/api/persons/1`, you just need the ID in the raw text field in Postman like this:
```json
{
  "id": 1
}
```


### Tasks

#### GET

Just go to `localhost:8080/api/tasks` in your browser. No need for Postman here.

#### POST

In Postman, `POST` the following to `localhost:8080/api/tasks`:
```json
{
  "name": "Task 1",
  "description": "This is a task",
  "creatorId": 4,
  "roomId": 7,
  "assigneeIds": [5, 6]
}
```

#### PUT
First, take note of the ID you want to `PUT`. Then, in Postman, use the `PUT` method and `localhost:8080/api/tasks/{id}` as the URL. Replace `{id}` with the ID you took note of. In the input field, put:

```json
{
  "id": 1,
  "title": "Task 1",
  "description": "This is a new description of the task and thus what we PUT into the new record.",
  "status": "Not started"
}
```

#### DELETE
Take note of the ID you want to `DELETE`. Go to `localhost:8080/api/tasks/{id}` in Postman and use the `DELETE` method. When you are on say `localhost:8080/api/tasks/1`, you just need the ID in the raw text field in Postman like this:
```json
{
  "id": 1
}
```

---

## API documentation

### 📁 com.nag.taskmanager

#### 📦 Controllers
- 📄 __ApiIndexController__
  - Provides a central navigation hub for discovering available API endpoints. See api-index.html for more information.
    - /api/
      - /api/rooms
      - /api/persons
      - /api/tasks
- 📄 PersonController
  - Controls the Person endpoints so that you can manage persons.
- 📄 RoomController
  - Controls the Room endpoints so that you can manage rooms.
- 📄 TaskController
  - Controls the Task endpoints so that you can manage tasks.
      
#### 📦 DTO (Data Transfer Objects)
- 📄 __PersonDTO__
  - Used for creating and updating persons not to expose the database. @NotBlank annotation is used for input validation. If the fields annotated with @NotBlank are left empty, a 400 Bad Request error will be returned to the user. Constructs a Person object from the PersonDTO object with the fields firstName, lastName, email, and phone.
- 📄 __RoomDTO__
  - Used for creating and updating rooms not to expose the database. @NotBlank annotation is used for input validation. If the fields annotated with @NotBlank are left empty, a 400 Bad Request error will be returned to the user. Constructs a Room object from the RoomDTO object with the fields name, description, and capacity.
- 📄 __TaskDTO__
  - Used for updating tasks not to expose the database. @NotBlank annotation is used for input validation. If the fields annotated with @NotBlank are left empty, a 400 Bad Request error will be returned to the user. Constructs a Task object from the TaskDTO object with the fields name, description, roomId, creatorId, assigneeIds, and status.
    
#### 📦 Models
- __Person__
    - Works as a model for the Person entity in the database. Also used for creating and updating persons.
- __Room__
  - Works as a model for the Room entity in the database. Also used for creating and updating rooms.
- __Task__
  - Works as a model for the Task entity in the database. Also used for creating and updating tasks.
      
#### 📦 Repositories
  - PersonRepository
    - Used for accessing the database.
  - RoomRepository
    - Used for accessing the database
  - TaskRepository
    - Used for accessing the database

#### 📦 Services
  - PersonService
    - Used for business logic
  - RoomService
    - Used for business logic
  - TaskService
    - Used for business logic
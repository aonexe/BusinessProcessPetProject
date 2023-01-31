# BusinessProcessPetProject

## Users api
A RESTful API for managing useres.

### Endpoints

#### Get all users

`GET /users`

Retrieves a list of all users.

##### Response


```json
[
    {
        "id": 1,
        "firstName": "Fname 1",
        "lastName": "Fname 1",
        "dateOfBirth": "2023-01-26T20:00:00.000+00:00",
        "email": "user1@gmail.com",
        "username": "user1",
        "userRole": "USER"
    },
    {
        "id": 2,
        "firstName": "Fname 2",
        "lastName": "Fname 2",
        "dateOfBirth": "2024-01-26T20:00:00.000+00:00",
        "email": "user2@gmail.com",
        "username": "user2",
        "userRole": "USER"
    }
]

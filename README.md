# Gymify

**Gymify** is a Spring Boot backend application designed for fitness tracking.  
It allows users to create accounts, save workouts, and add strength or cardio exercises.  
The project was built with **Java 21**, focusing on OOP principles (encapsulation, inheritance, polymorphism, abstraction), clean service architecture, and RESTful API design.

---

## Overview

**Gymify** is an educational demo project built to demonstrate:
- REST API design with Spring Boot  
- DTO mapping and layered architecture  
- Database persistence with JPA & Hibernate  
- Unit and integration testing  
- Swagger/OpenAPI documentation  
- Token-based authentication with **JWT**
  
The system manages:
- **Users** – with name, password, email, fitness goal, and profile image  
- **Workouts** – associated with each user and containing a list of exercises  
- **Exercises** – two main types: strength and cardio  
  - **StrengthExercise** → includes sets, reps, and weight  
  - **CardioExercise** → includes duration  

The application also includes:
- A predefined **exercise catalog** (strength and cardio) to choose from  
- A predefined **set of profile images** that users can select from  

---

## Core Features
- Register, authenticate, and manage profile  
- JWT token returned upon login  
- Upload or select a predefined profile image  
- Define personal fitness goals  

###  Workouts
- Create, edit, and delete workouts per user  
- Add both strength and cardio exercises  
- Track historical data  

###  Exercises
- Separate models for strength and cardio exercises  
- Predefined catalog (e.g., Bench Press, Running, Squats)  
- Strength → includes sets/reps/weight  
- Cardio → includes duration

  ##  Security with JWT

Gymify implements **token-based authentication** using **JSON Web Tokens (JWT)**.  
This ensures stateless, secure communication between the client and backend API.

###  Key Components

- **`AuthController`** – handles registration and login requests  
- **`JwtAuthenticationFilter`** – validates tokens from incoming requests  
- **`SecurityConfig`** – defines Spring Security filter chain and access rules  

###  JWT Authentication Flow + API Flow

1. **User Registration**  
   `POST /api/auth/register`  
   - User provides name, email, and password  
   - Password is hashed using `BCryptPasswordEncoder`  
   - User is persisted in database  

2. **Login**  
   `POST /api/auth/login`  
   - Credentials are verified  
   - A JWT token is generated and returned to the user  

3. **Access Protected Endpoints**  
   - Clients include token in the HTTP header:  
     ```
     Authorization: Bearer <token>
     ```
   - `JwtAuthenticationFilter` intercepts the request  
   - If token is valid → access granted  
   - Otherwise → `403 Forbidden`
5. The user can update his goal or the profile image.    
4. The user creates a workout (e.g., “Push Day”).  
3. The user adds exercises from the predefined catalog:
   - Bench Press → *Strength* (sets, reps, weight)  
   - Running → *Cardio* (duration)  
4. The workout and all exercises are saved to the database.  
5. The user can retrieve their workout history and exercise details via the REST API.
---

## Technical Stack

| Layer | Technology |
|--------|-------------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.6 |
| ORM | Spring Data JPA (Hibernate) |
| Database | H2 / PostgreSQL |
| Security | Spring Security + JWT + BCrypt |
| Build Tool | Maven |
| Testing | JUnit 5, Mockito, MockMvc |
| Documentation | Swagger / OpenAPI |
| Extras | Lombok |

---

## Object-Oriented Design

- **Encapsulation** – all entities (`User`, `Workout`, `Exercise`) have private fields and public getters/setters.  
- **Inheritance** – `Exercise` is an abstract class extended by `StrengthExercise` and `CardioExercise`.  
- **Polymorphism** – both `StrengthExercise` and `CardioExercise` override the `calculateCalories()` method differently, based on the exercise type. 
- **Abstraction** – repository interfaces (`UserRepository`, `WorkoutRepository`, `ExerciseRepository`) are defined and auto-implemented by Spring Data JPA.

---

## License

This project was developed for educational and portfolio purposes, to demonstrate Java OOP principles and Spring Boot backend development.

---

##  How to Run

### Requirements
- **Java 21+**
- **Maven 3.5.6+**
  
### The app will run at:
  http://localhost:8080

### Endpoints & testing (Postman / curl / Swagger) 
### Swagger UI - Available at:
http://localhost:8080/swagger-ui.html 
 

### Exemples API request
#### Register user (public)
`POST /api/auth/register`
Body JSON:
```json
{
  "name": "Alice",
  "email": "alice@example.com",
  "password": "securepass",
  "goal": "Lose weight",
  "imageId": 2
}
```

#### Login user (public)
`POST /api/auth/login`
Body JSON:
```json
{
  "email": "alice@example.com",
  "password": "securepass",
}
```
#### Create workout (authenticated)
`POST /api/workouts`
Body JSON:
```json
{
  "name": "Full Body Workout",
  "createTimestamp": "2025-10-27",
  "userId": 1,
  "exercises": [
    {
      "catalogId": 2,
      "sets": 5,
      "rep": 8,
      "weight": 100.0
    },
    {
      "catalogId": 35,
      "duration": 20
    }
  ]
}

```
#### Create exercises (authenticated)
`POST /api/exercises`
Body JSON:
```json
{
  "sets": 3,
  "reps": 10,
  "weight": 20.0,
  "workoutId": 2,
  "catalogId": 12
}


```
  
```bash
git clone https://github.com/<username>/Gymify.git
cd Gymify
mvn spring-boot:run

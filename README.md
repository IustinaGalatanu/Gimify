# Gymify

**Gymify** is a Spring Boot backend application designed for fitness tracking.  
It allows users to create accounts, save workouts, and add strength or cardio exercises.  
The project was built with **Java 21**, focusing on OOP principles (encapsulation, inheritance, polymorphism, abstraction), clean service architecture, and RESTful API design.

---

## Overview

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

- Create and manage user accounts (name, email, goal, image)  
- Add and update workouts for each user  
- Add exercises to workouts (strength or cardio)  
- Use a predefined catalog of exercises (e.g., “Bench Press”, “Running”)  
- Handle strength and cardio data separately  
  - Strength → reps, sets, and weight  
  - Cardio → duration  
- RESTful API with DTOs and clean JSON responses  
- Database persistence using Spring Data JPA  

---

## Technical Stack

| Layer | Technology |
|--------|-------------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.6 |
| ORM | Spring Data JPA (Hibernate) |
| Database | H2 / PostgreSQL |
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

## Example API Flow

1. A user registers with name, email, password, and goal.  
2. The user creates a workout (e.g., “Push Day”).  
3. The user adds exercises from the predefined catalog:
   - Bench Press → *Strength* (sets, reps, weight)  
   - Running → *Cardio* (duration)  
4. The workout and all exercises are saved to the database.  
5. The user can retrieve their workout history and exercise details via the REST API.

---

## License

This project was developed for educational and portfolio purposes, to demonstrate Java OOP principles and Spring Boot backend development.

---

##  How to Run

### Requirements
- **Java 21+**
- **Maven 3.9+**
  
### The app will run at:
  http://localhost:8080
  
### Swagger UI is automatically available once the app is running.

http://localhost:8080/api](http://localhost:8080/swagger-ui/index.html
  
```bash
git clone https://github.com/<username>/Gymify.git
cd Gymify
mvn spring-boot:run

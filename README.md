# Biblioteca API

REST API to manage a library catalog: create, list, search, update and delete books, and lend or return each one. Built with Java and Spring Boot, with a layered architecture and consistent HTTP error responses.

## Tech stack

- Java 21
- Spring Boot 4.1.1 (Spring Web, Spring Data JPA, Validation)
- MySQL 8
- Maven

## Features

- Full CRUD for books
- Lend and return a book, with state validation (a book that is already lent cannot be lent again)
- Unique ISBN, enforced both in the service and in the database
- List only the available books, and group book titles by author (both implemented with Java Streams)
- Input validation with Bean Validation
- Centralized error handling with `@RestControllerAdvice`
- DTOs for requests and responses, so the entity is never exposed directly

## Architecture

```
controller   HTTP endpoints, no business logic
service      business rules (ISBN check, lend/return state)
repository   Spring Data JPA, talks to MySQL
model        JPA entity
dto          request and response objects
exception    custom exceptions and the global handler
```

## Getting started

### Prerequisites

- JDK 21
- MySQL 8 running locally

### Setup

1. Create the database:

   ```sql
   CREATE DATABASE biblioteca_api;
   ```

2. Copy the example configuration and fill in your own MySQL credentials:

   ```
   copy src\main\resources\application.properties.example src\main\resources\application.properties
   ```

   `application.properties` is listed in `.gitignore`, so your credentials are never committed.

3. Run the application:

   ```
   .\mvnw spring-boot:run
   ```

   On macOS or Linux use `./mvnw spring-boot:run`. The API starts on `http://localhost:8080`. Tables are created automatically on startup.

## Endpoints

Base path: `/libros`

| Method | Path | Description |
|---|---|---|
| POST | `/libros` | Create a book |
| GET | `/libros` | List all books |
| GET | `/libros/{id}` | Get a book by id |
| PUT | `/libros/{id}` | Update a book (does not change availability) |
| DELETE | `/libros/{id}` | Delete a book |
| PATCH | `/libros/{id}/prestar` | Lend a book |
| PATCH | `/libros/{id}/devolver` | Return a book |
| GET | `/libros/disponibles` | List only available books |
| GET | `/libros/por-autor` | Book titles grouped by author |

### Request body (create and update)

```json
{
  "titulo": "Ficciones",
  "autor": "Borges",
  "anioPublicacion": 1944,
  "isbn": "9780802130303"
}
```

Validation rules: `titulo` and `autor` cannot be blank, `anioPublicacion` must be 1450 or later, and `isbn` is required and must be unique.

### Response

```json
{
  "id": 1,
  "titulo": "Ficciones",
  "autor": "Borges",
  "disponible": true
}
```

A new book always starts as available. Availability only changes through the lend and return endpoints.

## Error responses

| Status | When | Body |
|---|---|---|
| 400 | Invalid input | Map of field name to error message |
| 400 | Lending a book that is already lent, or returning one that is already available | Plain message |
| 404 | The book id does not exist | Plain message |
| 409 | The ISBN is already registered to another book | Plain message |

Example of a validation error:

```json
{
  "titulo": "Debe especificar el titulo",
  "anioPublicacion": "El anio debe ser mayor a 1450"
}
```

## Quick test with curl

```
curl -X POST http://localhost:8080/libros -H "Content-Type: application/json" -d "{\"titulo\":\"Ficciones\",\"autor\":\"Borges\",\"anioPublicacion\":1944,\"isbn\":\"9780802130303\"}"
curl http://localhost:8080/libros
curl -X PATCH http://localhost:8080/libros/1/prestar
```

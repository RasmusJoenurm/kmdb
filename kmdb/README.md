# Movie Database API

## 1. Project Overview

The Movie Database API is a robust RESTful API designed for managing a movie database. It enables users to perform CRUD operations on movies, actors, and genres. The API is built using Spring Boot, with SQLite as the backend database for persistent storage.

This project aims to simplify the management of movies, actors, and genres by providing structured endpoints for efficient data handling. Additional features include search capabilities, pagination, and robust error handling.

---

## 2. Setup and Installation Instructions

### Prerequisites
1. **Java 21**:
   - Verify installation: `java -version`
   - [Download Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) if not installed.

2. **Apache Maven**:
   - Verify installation: `mvn -version`
   - [Install Maven](https://maven.apache.org/install.html) or use Homebrew on macOS/Linux: `brew install maven`.

3. **SQLite** (Optional for local database browsing):
   - [Install SQLite](https://sqlite.org/download.html).

---

### Installation Steps

1. **Clone the Repository**:
   ```bash
   git clone https://gitea.kood.tech/rasmusjoenurm/kmdb.git
   cd kmdb
   ```

2. **Build the Application**:
   ```bash
   mvn package
   ```

3. **Run the Application**:
   ```bash
   java -jar target/movies_api-0.0.1.jar
   ```

4. **Stop the Application**:
   ```bash
   pkill -f movies_api-0.0.1.jar
   ```

---

### 3. Usage Guide

#### API Testing

You can test the Movie Database API using the following tools:

- **Postman Workspace**:  
  A pre-configured Postman workspace is available for testing.  
  [Postman Collection](https://www.postman.com/rasmusj7/movie-database-api/overview)  

  **How to Use the Postman Collection**:
  1. Download the provided `.json` Postman collection file.
  2. Open Postman and go to **File > Import**.
  3. Select the downloaded `.json` file.
  4. Use the imported collection to test endpoints such as retrieving movies, actors, and genres, or performing CRUD operations.

- **Swagger UI**:  
  Access the API documentation at [Swagger UI](http://localhost:8080/swagger-ui/index.html#/).  
  It provides detailed information about available endpoints, expected parameters, and response formats.

---

### Core Functionalities

- **CRUD Operations**:
  - Movies: Add, retrieve, update, and delete movies.
  - Actors: Manage actor details and associations.
  - Genres: Organize and categorize movies by genres.

- **Advanced Features**:
  - Search functionality:
    - Search movies by title (case-insensitive).
    - Filter actors by partial name.
    - Search genres by partial name.
  - Pagination:
    - Retrieve movies, actors, or genres by page and size.
  - Relationship management:
    - Associate movies with multiple actors and genres.
    - Fetch movies by genre, actor, or release year.

- **Error Handling**:
  - Robust validation for inputs.
  - User-friendly error messages and appropriate HTTP status codes.

  - **Star-Based Rating System**:
  - Movies can now be rated using a star-based system.
  - Ratings are between 1 and 5 stars.
  - To add a rating, use the following endpoint:
    - **Endpoint**: `PATCH /api/movies/{movieId}/rate`
    - **Query Parameter**: `?stars=<number_of_stars>`
    - Example:
      ```bash
      PATCH http://localhost:8080/api/movies/1/rate?stars=4
      ```
    - The JSON response will include a `"Rating"` field displaying stars, e.g., `"★★★★☆"`.

---

## 4. Additional Features and Enhancements

- **Statistics**:
  - Endpoints to get the count of movies, actors, and genres in the database.

- **Swagger Integration**:
  - Provides a visual interface for exploring and testing API endpoints.

- **Database Initialization**:
  - Sample data included for testing:
    - Genres: At least 5 genres (Action, Comedy, Drama, Thriller, Sci-Fi).
    - Movies: 20+ movies spanning at least two decades.
    - Actors: 15+ actors, each associated with at least one movie.

---

## 5. Troubleshooting

### Common Issues
1. **Port Already in Use**:
   - Solution: Terminate the process using port 8080 or configure the application to use a different port in `application.properties`.

2. **Database Connection Errors**:
   - Verify `application.properties` for correct database configuration:
     ```properties
     spring.datasource.url=jdbc:sqlite:./kmdb.db
     spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
     ```

3. **Invalid API Responses**:
   - Ensure data consistency in the SQLite database (e.g., valid relationships between movies, actors, and genres).

---

## 6. Contribution and Development

### Code Structure
- **Controllers**:
  - RESTful endpoints for handling API requests.
- **Services**:
  - Core business logic.
- **Repositories**:
  - Data access layer interacting with the SQLite database.

### Development Notes
- Follow Java and Spring Boot best practices.
- Ensure consistency in coding style and proper documentation.

### Running Tests
- Use Postman or Swagger UI for functional testing.
- Future enhancements include automated unit and integration tests.

---

Thank you for using the Movie Database API! 🎬

 * Also, you can use the added springdoc-openapi dependency by going to: \
    `http://localhost:8080/swagger-ui/index.html#/` \
    This will allow you to see which paths and parameters are expected.

## 7. Any Additional Features or Bonus Functionality Implemented
- Functionality for getting the number of movies, actors and genres in database
- Case-insensitive search functionality for finding movies by partial title, actors by partial name and genres by partial name.
- Pagination functionality for GET requests to retrieve movies, genres and actors by page number and page size.
- Added springdoc-openapi dependency so Swagger UI can be used to see expected paths and parameters for the API.
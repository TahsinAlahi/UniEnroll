# UniEnroll - College/University Course Management System

A Pure Java implementation of a College/University Course Management System using Clean Architecture principles and Jackson for JSON persistence.

## Architecture

The project is structured following Clean Architecture:

*   **`domain`**: Contains the core business entities (`Member`, `Student`, `Admin`, `Course`) and enums (`Roles`). These objects contain pure business logic and validation, independent of any frameworks.
*   **`exception`**: Contains custom unchecked exceptions (`ValidationException`, `NotFoundException`, `DuplicateResourceException`, `UnauthorizedException`) for robust and declarative error handling.
*   **`repository`**: Defines interfaces for data access (`Repository`, `MemberRepository`, `CourseRepository`). This allows the application to depend on abstractions rather than concrete database implementations (Dependency Inversion Principle).
*   **`application`**: The Use Case layer. Contains services (`MemberService`, `CourseService`) that orchestrate domain objects and interact with repository interfaces to fulfill business requirements.
*   **`infrastructure.file`**: Contains the concrete implementations of the repositories (`FileMemberRepository`, `FileCourseRepository`) using Jackson to read and write data to local JSON files (`./data/`).
*   **`Main.java`**: The entry point. Handles manual dependency injection and demonstrates the system's capabilities.

## Key Design Decisions

1.  **JSON Persistence**: Data is saved to a local `./data/` directory relative to the current working directory. This ensures the application can run reliably when packaged as a JAR, avoiding the brittleness of writing back to the `src/main/resources` classpath directory.
2.  **UUIDs for Identity**: Entities use `UUID.randomUUID().toString()` for reliable, globally unique ID generation, avoiding issues with static counters resetting upon application restart.
3.  **Exception-Driven Flow**: Business logic errors (e.g., duplicate emails, unauthorized access, full courses) throw custom exceptions rather than returning `null` or printing to the console. This makes the services robust and the UI/Entrypoint responsible for handling presentation.
4.  **Extensibility**: By depending on the `MemberRepository` and `CourseRepository` interfaces, migrating to a real database (like PostgreSQL or MySQL via JDBC/Hibernate) simply requires writing new repository implementations (e.g., `JdbcMemberRepository`) and injecting them at startup.

## How to Run

### Prerequisites
*   Java 17 or higher
*   Maven

### Compile and Execute
1.  Compile the project:
    ```bash
    mvn compile
    ```
2.  Run the application:
    ```bash
    mvn exec:java -Dexec.mainClass="unienroll.Main"
    ```
    
*(Note: If you run it from an IDE, simply execute the `Main` class. The `data` folder will be created in your project root.)*

### Expected Output
The `Main.java` file provides a demonstration of:
*   Registering an Admin and a Student.
*   Handling duplicate registration attempts.
*   Verifying a student by an Admin.
*   Creating a Course.
*   Enrolling the student in the course.
*   Persisting all changes to `data/members.json` and `data/courses.json`.

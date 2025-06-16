# Asset Management - Backend ⚙️
---

## Overview 🌟

This repository contains the backend for the Asset Management System. It serves as the core API service, handling data storage, business logic, and providing the necessary endpoints for the frontend application. Built with Java, it ensures robust and scalable asset management operations.

## Frontend Integration 🌐

This backend service is designed to work seamlessly with its corresponding frontend application:

- Frontend Repository: https://github.com/SethyRung/Asset-Management-Frontend
- Live Demo: https://asset-management-sethyrung.vercel.app/

## Features ✨

This backend provides the foundational functionalities for an asset management system, typically including:

- **RESTful API Endpoints**: Exposes APIs for managing assets, users, and other related entities.
- **Data Persistence**: Manages data storage, likely using a database (e.g., PostgreSQL, MySQL, H2 for development).
- **Business Logic**: Implements core business rules for asset tracking, updates, and more.
- **Authentication & Authorization (Potential)**: Secure access to API endpoints.
- **Scalability**: Designed to handle a growing number of assets and users.

## Technologies Used 🛠️

This backend project is primarily built with:

- **Java**: The core programming language for the server-side application.
- **Spring Boot (Likely)**: Given the Java and Gradle setup, Spring Boot is a strong candidate for building robust and easy-to-deploy applications.
- **Gradle**: The build automation tool for managing dependencies and building the project.
- **Docker & Docker Compose**: For containerization, enabling easy setup and deployment across different environments.
- **Database (Likely)**: A relational database for data storage (e.g., H2 for embedded/development, PostgreSQL/MySQL for production).

## Installation & Setup 🚀

To get this backend service running on your local machine, follow these steps:

1. Prerequisites:

    - Java Development Kit (JDK): Ensure you have JDK 11 or newer installed.
    - Docker & Docker Compose: Install Docker Desktop (includes Docker Compose) for containerized setup.
    - Editor/IDE: An IDE like IntelliJ IDEA or VS Code (with Java extensions) is recommended.

2. Clone the Repository:

    `git clone https://https://github.com/SethyRung/Asset-Management-Backend.git`

3. Navigate to Project Directory:

    `cd Asset-Management-Backend`

4. Environment Variables:

    - Copy the `.env-example` file to `.env` and configure any necessary environment variables, such as database connection strings or API keys.
        
        ```bash
        cp .env-example .env
        # Open .env and adjust variables, e.g.:
        # DATABASE_URL=jdbc:h2:mem:assetdb
        # DATABASE_USERNAME=sa
        # DATABASE_PASSWORD=
        ```

5. Build and Run with Docker Compose (Recommended) 🐳:

    - This is the easiest way to run the application along with its dependencies (e.g., database).
        
        `docker-compose up --build`

        This command will build the Docker image for the backend and start all services defined in docker-compose.yml. The API should be accessible at http://localhost:8080 (or the port configured in application.properties/application.yml).

6. Build and Run Natively (Alternative) 🖥️:

    - If you prefer not to use Docker, you can build and run the Spring Boot application directly:
        
        ```bash
        ./gradlew clean build
        java -jar build/libs/asset-management-backend-0.0.1-SNAPSHOT.jar # Adjust version as needed
        ```

## API Endpoints 🔌

(Note: Specific endpoints will depend on your implementation. Here are common examples.)

The backend typically exposes RESTful API endpoints at `http://localhost:8080/api/v1/` (or your configured base URL), for example:
    
    - `GET /api/v1/assets`: Retrieve all assets.
    - `GET /api/v1/assets/{id}`: Retrieve a specific asset by ID.
    - `POST /api/v1/assets`: Create a new asset.
    - `PUT /api/v1/assets/{id}`: Update an existing asset.
    - `DELETE /api/v1/assets/{id}`: Delete an asset.
    - `POST /api/v1/auth/login`: User login.
    - `POST /api/v1/auth/register`: User registration.

Refer to the source code (e.g., controller classes in `src/main/java/.../controller/`) for the exact API specifications.

## Project Structure 📁

The project follows a standard Spring Boot/Gradle structure:

```bash
Asset-Management-Backend/
├── gradle/                 # Gradle wrapper files
├── src/                    # Source code
│   ├── main/
│   │   ├── java/           # Java source files
│   │   │   └── com/
│   │   │       └── asset_management/
│   │   │           ├── AssetManagementBackendApplication.java # Main application file
│   │   │           ├── controller/       # REST API controllers
│   │   │           ├── service/          # Business logic services
│   │   │           ├── repository/       # Data access layer (e.g., Spring Data JPA)
│   │   │           └── model/            # Data models/entities
│   │   └── resources/      # Application resources (properties, templates)
│   │       ├── application.properties  # Spring Boot configuration
│   │       └── ...
│   └── test/               # Test code
├── .env-example            # Example environment variables
├── .gitignore              # Files/folders to ignore in Git
├── Dockerfile              # Docker image definition
├── build.gradle            # Gradle build configuration
├── docker-compose.yml      # Docker Compose configuration
├── gradlew                 # Gradle wrapper script (Linux/macOS)
├── gradlew.bat             # Gradle wrapper script (Windows)
├── settings.gradle         # Gradle settings file
└── README.md               # This file
```

## Contributing 🤝

Contributions are welcome! If you'd like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature-name`).
3. Make your changes and commit them (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/your-feature-name`).
5. Create a Pull Request.

## License

This project is licensed under the MIT License.

## Contact

For any inquiries or suggestions, you can reach out to [Sethy Rung](https://github.com/SethyRung) via GitHub.

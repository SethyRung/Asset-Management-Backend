# Asset Management - Backend ⚙️

## Overview 🌟

This repository contains the **backend service** for the Asset Management System.
It powers the system’s core APIs, manages data persistence, enforces business logic, and integrates authentication & authorization.

Built with **Spring Boot** and **PostgreSQL**, it ensures scalable and secure asset management operations.

## Frontend Integration 🌐

This backend works with the following frontend application:

* **Frontend Repository**: [Asset-Management-Frontend](https://github.com/SethyRung/Asset-Management-Frontend)
* **Live Demo**: [https://asset-management-sethyrung.vercel.app/](https://asset-management-sethyrung.vercel.app/)

---

## Features ✨

* **RESTful APIs** for assets, users, and authentication
* **PostgreSQL persistence** (with Dockerized database)
* **Authentication & Authorization** with JWT
* **Email & file upload support** (via environment configuration)
* **Docker & Docker Compose** for easy local setup and deployment
* **Gradle build system** with build caching

---

## Technologies 🛠️

* **Java 21** (via Eclipse Temurin)
* **Spring Boot 3**
* **Gradle**
* **PostgreSQL**
* **Docker & Docker Compose**

---

## Getting Started 🚀

### 1. Prerequisites

* [Docker & Docker Compose](https://docs.docker.com/get-docker/)
* Java 21 (only needed for native builds)
* Gradle (optional, project includes `gradlew`)

---

### 2. Clone the Repository

```bash
git clone https://github.com/SethyRung/Asset-Management-Backend.git
cd Asset-Management-Backend
```

---

### 3. Setup Environment Variables

Copy the example env file and adjust values as needed:

```bash
cp .env-example .env
```

Example important variables:

```ini
# PostgreSQL
POSTGRES_DB=asset_management
POSTGRES_USER=admin
POSTGRES_PASSWORD=supersecret
```

---

### 4. Run with Docker Compose 🐳 (Recommended)

Build and start all services (backend + PostgreSQL):

```bash
docker-compose up --build
```

* Backend API: [http://localhost:8080](http://localhost:8080)
* PostgreSQL DB: `localhost:5432` (inside Docker network use `postgres-db:5432`)

To run in background:

```bash
docker-compose up -d
```

Stop containers:

```bash
docker-compose down
```

---

### 5. Native Development Setup (Optional) 🖥️

If you prefer running locally without Docker:

```bash
# Build the project
./gradlew clean build

# Run the JAR
java -jar build/libs/asset-management-backend-0.0.1-SNAPSHOT.jar
```

Make sure you have PostgreSQL running locally and update `.env`.

---

## Development Workflow 🔄 (Hot Reloading with Docker)

When developing, you don’t want to rebuild the Docker image every time you change code.
Instead, you can use `spring-boot-devtools` + **volume mounting** for hot reloading.

### 1. Enable Devtools

Add `spring-boot-devtools` as a dependency in your `build.gradle`:

```gradle
dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}
```

---

### 2. Create `docker-compose.override.yml`

This file overrides the default setup for local development:

```yaml
version: "1.0.0"

services:
  backend:
    volumes:
      - ./src:/app/src        # Mount source code
      - ./build:/app/build    # Mount build folder
    command: ./gradlew bootRun --no-daemon
    environment:
      SPRING_PROFILES_ACTIVE: dev
```

---

### 3. Run in Dev Mode

```bash
docker-compose -f docker-compose.yml -f docker-compose.override.yml up --build
```

* The backend will now start with `bootRun`.
* Any Java class changes will trigger **auto-restart**.
* Resource changes (like `.properties` files) reload without restarting.

---

## API Endpoints 🔌

The API is served at `http://localhost:8080/api/`

Examples:

* `POST /api/auth/register` → Register a new user
* `POST /api/auth/login` → Login and receive JWT
* `GET /api/assets` → List all assets
* `POST /api/assets` → Create a new asset
* `PUT /api/assets/{id}` → Update an asset
* `DELETE /api/assets/{id}` → Remove an asset

---

## Project Structure 📁

```
Asset-Management-Backend/
├── src/main/java/com/asset_management/
│   ├── AssetManagementBackendApplication.java  # Main entry point
│   ├── controller/   # REST Controllers
│   ├── service/      # Business logic
│   ├── repository/   # Data access (Spring Data JPA)
│   └── model/        # Entities
├── src/main/resources/
│   └── application.properties
├── Dockerfile
├── docker-compose.yml
├── docker-compose.override.yml   # Development overrides (optional)
├── .env-example
├── build.gradle
└── README.md
```

---

## Contributing 🤝

1. Fork the repo
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -m "Add new feature"`)
4. Push branch (`git push origin feature/new-feature`)
5. Open a Pull Request

---

## License 📜

This project is licensed under the [MIT License](LICENSE).

---

## Contact 📬

* **Author**: [Sethy Rung](https://github.com/SethyRung)
* For questions or suggestions, feel free to open an issue or reach out via GitHub.

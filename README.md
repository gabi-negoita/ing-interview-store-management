# ISM (Ing Store Management) Service

Welcome to the **ISM (Ing Store Management) Service**! This service provides secure and efficient management of store
products through a RESTful API. Easily perform CRUD operations on products and categories, all powered by an in-memory
H2 database for quick setup and testing.

## 🚀 Features

- **RESTful API**: Secure endpoints for managing products and categories.
- **In-Memory Database**: Uses H2 to store data with schema and sample data loaded at startup.
- **Easy Configuration**: Customize the database, port, and context path through simple configuration files.
- **Debug Mode**: Convenient debug profile to troubleshoot and fine-tune your application.

## 📚 Description

ISM Service leverages an in-memory database (H2) that automatically creates the schema
(executes `schema.sql` file) and populates it with initial data (executes `data.sql` file) on
application startup. This is achieved using the following properties in the `application.properties` file:

```properties
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

### Database Configuration

The database settings are defined as follows:

```properties
spring.datasource.url=jdbc:h2:mem:test-db
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.datasource.username=sa
spring.datasource.password=pass
```

### Application Configuration

- **Port**: The application runs on a port specified by the `server.port` property:

    ```properties
    server.port=8080
    ```

- **Base Path**: The base URL for all API endpoints is configured via the `server.servlet.context-path` property:

    ```properties
    server.servlet.context-path=/ism/v1
    ```

## 🛠 How to Run Locally

### Prerequisites

Before running the ISM Service locally, ensure you have the following:

1. **Java 17** installed
2. **Git** installed (optional, as you can download the repository as a ZIP file)
3. **Maven** installed (optional, as you can use the provided Maven Wrapper `mvnw`)

### Steps to Get Started

1. **Clone the Repository**:

    ```bash
    git clone https://github.com/gabi-negoita/ism-service.git
    ```

2. **Build the Project**: This will compile the code, run tests, and package the application into a `.jar` file.

    ```bash
    ./mvnw clean install
    ```

3. **Run the Application**:

    ```bash
    ./mvnw spring-boot:run
    ```

4. **Run in Debug Mode**: Use the debug profile for enhanced logging and debugging. You can also enable this in your IDE
   by adding the `-Dspring.profiles.active=debug` VM option.

    ```bash
    ./mvnw spring-boot:run -Dspring-boot.run.profiles=debug
    ```

## 🏃‍♂️ Quick Start with Docker

If you have Docker installed, you can quickly run the ISM Service with just a single command using `make`. This
simplifies the setup and execution process. Ensure Docker is installed before using these commands.

### Prerequisites

1. 🐳 **Docker** installed
2. Maven installed (it uses the mvn command to build the project)

### Commands

- **Run the Application**: Start the application in a Docker container.

    ```bash
    make run
    ```

- **Run in Debug Mode**: Start the application in debug mode within a Docker container.

    ```bash
    make debug
    ```

- **Stop and Remove Container**: Stop and remove the running Docker container.

    ```bash
    make stop
    ```
- **Stop, Remove Container and Remove Image**: Stop and remove the running Docker container and the image created.

    ```bash
    make clean
    ```

> ⚠️ Note that the application runs on port `8080` inside the Docker container and is mapped to port `9090` on the host,
> so make sure port `9090` is available on your machine or change the port mapping in the `makefile` file.

## 🔐 How to Use the Application

The ISM Service is secured, meaning all endpoints are protected and require proper authentication and authorization.

### 1. **User Authentication and Token Generation**

The application uses permission-based security. Each endpoint requires specific permissions that are granted to
authenticated users. Default users for `basic` and `admin` level permissions are pre-defined in the `data.sql` file.
You can use these users or create additional ones as needed by adding insert entries to the `data.sql` file.

> ⚠️ Note that the password for all users is `pass`, but they are encrypted in the database
> using `BCryptPasswordEncoder`..

### 2. **Generate an Authentication Token**

To interact with the secured endpoints, you first need to obtain a token. You can use the pre-defined user credentials
to generate this token. Make a `POST` request to the `/auth/token` endpoint with the user's credentials.

#### Example Request:

   ``` bash
curl -X POST \
  http://localhost:9090/ism/v1/auth/token \
  -H "Content-Type: application/json" \
  -d '{"username": "basic", "password": "pass"}'
   ```

### 3. **Using the Token to Access Secured Endpoints**

Once you have the token, include it in the `Authorization` header of your requests to access products and categories.

#### Example Request with Token:

```bash
curl -X GET \
  http://localhost:9090/ism/v1/products \
  -H "Authorization: Bearer <ACCESS_TOKEN>"
```

Replace `<ACCESS_TOKEN>` with the actual token string you received from the `/auth/token` endpoint. This token grants
you access to the application's secured resources based on the permissions attached to the authenticated user.

## 🔓 Endpoint Access Control

**GET Endpoints**: The `GET` endpoints (e.g., `/products`) are designed for read access. Any user with read permissions
can retrieve data from these endpoints.

**Other Endpoints**: Endpoints that perform operations like `POST`, `PUT`, or `DELETE` require write or delete access.
These operations modify data, so the user must have appropriate write permissions to interact with these endpoints.

> ⚠️ Ensure you use a user account with the correct permissions. If you're accessing `GET` endpoints, a user
> with read access is sufficient. However, for creating, updating, or deleting resources, make sure the user has the
> necessary write or delete permissions. This is crucial to avoid authorization errors.

## 🎉 Thank You!

Thank you for using the ISM Service! We hope you find it helpful for managing your store's products and categories.

Happy coding! 🚀

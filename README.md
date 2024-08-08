# ISM (Ing Store Management) Service

## Description

ISM (Ing Store Management) Service is a service that provides the ability to manage products in a store.
Provides a REST API for CRUD operations for managing products.

It uses an in-memory database (H2) to store the data.
When running the application, the database is creating the schema (see `schema.sql`) and inserting some data into the
tables (see `data.sql`), which is done by using these properties in the `application.properties` file:

```
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

The database configuration is defined in the `application.properties` file using these properties:

```
spring.datasource.url=jdbc:h2:mem:test-db
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.datasource.username=sa
spring.datasource.password=pass
```

The application will run at a port specified by this property in the `application.properties` file:

```
server.port=9090
```

The base path is defined in the `application.properties` file using this property:

```
server.servlet.context-path=/ism/v1
```

## How to run locally

### Prerequisites

1. [x] Make sure you have **Java 17** installed
2. [x] Make sure you have **Maven** installed (Optional - because you can use the Maven Wrapper `mvnw`)

### Steps

1. Clone the repository
2. Build the project: will compile, run tests and package your application into a `.jar` file

```
./mvnw clean install
```

3. Run the application

```
./mvnw spring-boot:run
```

4. Run the application in **debug mode** by using the debug profile (or add the `-Dspring.profiles.active=debug` VM
   option if you are using an IDE)

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=debug
```

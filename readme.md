# Challenge wishlist

## Overview

This is a Spring Boot application built with Java 21. The application provides a REST API and uses MongoDB as its database. Swagger is integrated for API documentation.

## Prerequisites

- Java 17
- Docker & Docker Compose

## Features

- **Spring Boot**: A robust framework for building Java applications.
- **MongoDB**: A NoSQL database used to store application data.
- **Swagger**: Automatically generated API documentation.

## Setup Instructions

### 1. Clone the Repository

```bash
git clone git@github.com:SergioLacerda/challenge-wishlist-java.git
cd challenge-wishlist-java
```

### 2. Set Up MongoDB

#### Option 1: Use Docker Compose to Set Up MongoDB

To spin up a MongoDB instance using Docker Compose, run the following command:

```bash
docker-compose up -d
```
This will start MongoDB in a detached mode.

```bash
docker-compose down -v
```
This will stop MongoDB instance on your local machine.

#### Option 2: Use an Existing MongoDB Instance

If you already have a MongoDB instance running, you can configure the application to connect to it by setting the `ENV_DB_HOST` environment variable with your MongoDB URI.

```bash
export ENV_DB_HOST=mongodb://<username>:<password>@<host>:<port>/<database>
```

Ensure to replace `<username>`, `<password>`, `<host>`, `<port>`, and `<database>` with your actual MongoDB credentials and connection details.

### 3. Run the Application

After setting up MongoDB, you can run the Spring Boot application using the following command:

```bash
./mvnw spring-boot:run
```

### 4. Access Swagger Documentation

Once the application is running, you can access the Swagger UI for API documentation at:

[Swagger UI](http://localhost:8080/api/swagger-ui/index.html)



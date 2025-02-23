# E-Commerce Platform API

This is a **SpringBoot** application that provides a **RESTful API** for managing products, orders, and order items in an e-commerce platform. Built with **Java 21**, **Spring Data JPA**, and **Docker**, this application is designed to be scalable, maintainable, and easy to deploy.

## Key Features

- **Soft Delete Functionality**: Instead of permanently deleting records, the API uses a `deleted` flag to mark entries as inactive. This ensures historical data is preserved for auditing and reporting purposes while keeping the active dataset clean and efficient.
- **OpenAPI 3.0 Documentation**: The API is fully documented using **OpenAPI 3.1**, and a **Swagger UI** interface is provided for easy exploration and testing of endpoints. Access the Swagger UI at:  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Comprehensive Testing**: The application includes a suite of **unit** and **integration tests** written with **JUnit** and **Mockito**. A **code coverage report** is generated during testing, providing insights into the percentage of code covered by tests. The report can be viewed at:  
  `htmlReport/index.html` (open with any browser).
- **Docker Support**: The application is containerized using **Docker**, making it easy to deploy and run in any environment.

## Requirements

- **Maven** (for building the project)
- **Java 21** (JDK 21)
- **Docker** (for containerization)
- **PostgreSQL** (as the database)

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/EfrenRicardoInzunzaValenzuela/ecommerce.git
cd ecommerce 
```
### 2. Build the Docker Image

```bash
docker build -t my-spring-app .
```

### 3. Clone the Repository

```bash
docker-compose up -d
```
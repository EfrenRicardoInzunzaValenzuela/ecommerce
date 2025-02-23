# E-Commerce Platform API

This is a SpringBoot application that provides a RESTful API for managing products, orders, and order items in an e-commerce platform. The application is built using Java 21, SpringBoot, Spring Data JPA, and runs in a Docker container.

This API implements soft delete for all elements (products, orders, and order items) to maintain historical records and ensure data integrity. Instead of permanently removing records from the database, a deleted flag or status is used to mark entries as inactive. This approach allows the system to retain historical data for auditing and reporting purposes while keeping the active dataset clean and efficient. Additionally, the API is documented using OpenAPI 3.1, and a Swagger UI interface is provided for easy visualization and interaction with the API endpoints. Developers can access the Swagger UI at http://localhost:8080/swagger-ui.html to explore the API's functionality, test endpoints, and review the schema. To ensure code quality and reliability, the application includes a comprehensive suite of unit and integration tests written with JUnit and Mockito. A code coverage report is generated during the testing process, providing insights into the percentage of code covered by tests and helping to identify areas that may require additional testing or improvement. This combination of soft delete, Swagger UI, and rigorous testing ensures a robust, maintainable, and user-friendly API.

## Requirements

- Maven
- Java 21
- Docker
- PostgreSQL

## Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/e-commerce-platform.git
   cd e-commerce-platform

2. **Generate Docker Image**

   execute this on the project folder 

   ```bash
   docker build -t my-spring-app .

3. **Deployment**

  execute this on the project folder 

   ```bash
   docker-compose up -d
   

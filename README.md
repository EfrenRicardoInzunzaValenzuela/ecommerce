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

## Future Improvements
Here are some potential enhancements to improve the functionality and scalability of the e-commerce platform:

### 1. Inventory Control:

Implement inventory management to track product stock levels in real-time.

Add validation during order creation to ensure sufficient stock is available before processing an order.

Support multiple warehouses by associating inventory with specific locations and managing stock across them.

### 2. User Authentication and Authorization:

Integrate Spring Security to add user roles (e.g., Admin, Customer) and secure API endpoints.

Implement JWT (JSON Web Tokens) for stateless authentication.

### 3. Order Status Tracking:

Add an order status field (e.g., PENDING, SHIPPED, DELIVERED, CANCELLED) to track the lifecycle of an order.

Implement webhooks or notifications to inform customers about order updates.

### 4. Pagination and Filtering:

Add pagination and filtering support for large datasets (e.g., products, orders) to improve performance and usability.

### 5. Payment Gateway Integration:

Integrate with popular payment gateways (e.g., Stripe, PayPal) to handle online payments securely.

### 6. Analytics and Reporting:

Add endpoints to generate sales reports, product performance metrics, and customer insights.

Use tools like Spring Batch for processing large datasets and generating reports.

### 7. Caching:

Implement caching (e.g., using Redis) to improve response times for frequently accessed data, such as product listings.

### 8. Search Functionality:

Add a search feature for products using Elasticsearch or Apache Lucene for fast and efficient full-text search.

### 9. Multi-Tenancy Support:

Extend the application to support multiple tenants (e.g., different stores or businesses) with isolated data and configurations.

### 10. Logging and Monitoring:

Integrate logging frameworks like Logback or Log4j for better debugging and monitoring.

Use tools like Prometheus and Grafana for application performance monitoring.

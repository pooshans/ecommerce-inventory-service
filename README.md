# E-Commerce Inventory Management System

## Project Overview

The E-Commerce Inventory Management System is a Spring Boot-based application designed to manage products, categories, and SKUs for an e-commerce platform. It provides RESTful APIs for CRUD operations and includes features such as validation, logging, and API documentation using OpenAPI/Swagger.

## Setup Instructions

### Prerequisites

* **Java**: JDK 17 or higher
* **Maven**: Version 3.6 or higher
* **Database**: PostgreSQL (or any other supported database)
* **IDE**: IntelliJ IDEA (recommended)

### Steps to Set Up

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd ecommerce-inventory-service
   ```

2. **Configure the database:**

   Update the database connection properties in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Build the project:**

   ```bash
   mvn clean install
   ```

4. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

5. **Access the Swagger UI for API documentation:**

   Navigate to `http://localhost:8080/swagger-ui.html` or `http://localhost:8080/swagger-ui`

## API Endpoints Documentation

### Category Management

| Method | Endpoint               | Description           |
| ------ | ---------------------- | --------------------- |
| POST   | `/api/categories`      | Create a new category |
| GET    | `/api/categories`      | Get all categories    |
| GET    | `/api/categories/{id}` | Get category by ID    |
| PUT    | `/api/categories/{id}` | Update category by ID |
| DELETE | `/api/categories/{id}` | Delete category by ID |

### Product Management

| Method | Endpoint             | Description                                        |
| ------ | -------------------- | -------------------------------------------------- |
| POST   | `/api/products`      | Create a new product                               |
| GET    | `/api/products`      | Get all products (with search, filter, pagination) |
| GET    | `/api/products/{id}` | Get product by ID                                  |
| PUT    | `/api/products/{id}` | Update product by ID                               |
| DELETE | `/api/products/{id}` | Delete product by ID                               |

**Product Search & Filter Parameters:**

* `search`: Search by product name
* `category`: Filter by category name
* `minPrice`: Minimum price filter
* `maxPrice`: Maximum price filter
* `page`: Page number (default: 0)
* `size`: Page size (default: 10)

### SKU Management

| Method | Endpoint                         | Description                    |
| ------ | -------------------------------- | ------------------------------ |
| POST   | `/api/products/{productId}/skus` | Create a new SKU for a product |
| GET    | `/api/products/{productId}/skus` | Get all SKUs for a product     |
| GET    | `/api/skus/{id}`                 | Get SKU by ID                  |
| PUT    | `/api/skus/{id}`                 | Update SKU by ID               |
| DELETE | `/api/skus/{id}`                 | Delete SKU by ID               |

## Testing Instructions

### Run Unit Tests

Execute the following command to run all unit tests:

```bash
mvn test
```

View the test results in the console or check the generated reports in the `target/surefire-reports` directory.


### Test Coverage Report

To generate a test coverage report:

```bash
mvn test jacoco:report
```

The coverage report will be available in `target/site/jacoco/index.html`

## Technology Stack

### Backend

* **Java**: JDK 17
* **Spring Boot**: Framework for building RESTful APIs
* **Spring Data JPA**: ORM for database interactions
* **PostgreSQL**: Relational database (configurable)
* **H2 Database**: In-memory database for testing

### API Documentation

* **OpenAPI/Swagger**: API documentation and testing

### Validation

* **Jakarta Validation**: Field-level and custom validation

### Logging

* **SLF4J**: Logging framework
* **Logback**: Default logging implementation

### Testing

* **JUnit 5**: Unit testing framework
* **Mockito**: Mocking framework for testing
* **Spring Boot Test**: Integration testing support

### Build & Dependencies

* **Maven**: Build automation tool
* **Lombok**: Boilerplate code reduction

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/talentica/ecommerce/inventory/
│   │       ├── EcommerceInventoryServiceApplication.java
│   │       ├── controllers/
│   │       │   ├── CategoryController.java
│   │       │   ├── ProductController.java
│   │       │   └── SKUController.java
│   │       ├── services/
│   │       │   ├── CategoryService.java
│   │       │   ├── ProductService.java
│   │       │   └── SKUService.java
│   │       ├── repositories/
│   │       │   ├── CategoryRepository.java
│   │       │   ├── ProductRepository.java
│   │       │   └── SKURepository.java
│   │       ├── entities/
│   │       │   ├── Category.java
│   │       │   ├── Product.java
│   │       │   └── SKU.java
│   │       ├── dtos/
│   │       │   ├── CategoryRequestDTO.java
│   │       │   ├── CategoryResponseDTO.java
│   │       │   ├── ProductRequestDTO.java
│   │       │   ├── ProductResponseDTO.java
│   │       │   ├── ProductSummaryDTO.java
│   │       │   ├── SKURequestDTO.java
│   │       │   ├── SKUResponseDTO.java
│   │       │   ├── ErrorResponseDTO.java
│   │       │   └── PagedResponseDTO.java
│   │       └── exceptions/
│   │           ├── ResourceNotFoundException.java
│   │           ├── ValidationException.java
│   │           ├── BusinessLogicException.java
│   │           ├── DuplicateResourceException.java
│   │           └── GlobalExceptionHandler.java
│   └── resources/
│       ├── application.properties
│       └── logback-spring.xml
└── test/
    └── java/
        └── com/talentica/ecommerce/inventory/
            ├── services/
            │   ├── CategoryServiceTest.java
            │   ├── ProductServiceTest.java
            │   └── SKUServiceTest.java
            └── controllers/
                ├── CategoryControllerTest.java
                ├── ProductControllerTest.java
                └── SKUControllerTest.java
```

## Features

* **Complete CRUD Operations**: Full Create, Read, Update, Delete functionality for all entities
* **Search & Filtering**: Advanced search capabilities with multiple filter options
* **Pagination**: Efficient pagination support for large datasets
* **Validation**: Comprehensive input validation with custom error messages
* **Exception Handling**: Global exception handling with proper HTTP status codes
* **API Documentation**: Interactive Swagger UI for API testing
* **Unit & Integration Tests**: Comprehensive test coverage
* **Logging**: Structured logging for debugging and monitoring

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Contact

For any questions or support, please contact:

* **Developer**: \[Pooshan]
* **Email**: \[[pooshan.singh@talentica.com](mailto:your.email@talentica.com)]
* **Company**: Talentica Software Pvt. Ltd.

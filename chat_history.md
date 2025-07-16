Here's a detailed sequence of steps to ask GitHub Copilot to build this application step by step:

## Phase 1: Project Setup & Configuration

### Step 1: Project Structure
```
"Create a Spring Boot project structure for an e-commerce inventory management system. Include packages for entities, repositories, services, controllers, dtos, config, and exceptions. Show me the complete folder structure."

used the script ./create_project_structure.sh
```

### Step 2: Dependencies & Configuration
```
"Generate a complete pom.xml file for Spring Boot 3.x with dependencies for:
- Spring Boot Web
- Spring Data JPA
- H2 Database
- Validation
- Spring Boot Test
- OpenAPI/Swagger
- Lombok"
```

### Step 3: Application Properties
```
"Create application.properties file with H2 database configuration, JPA settings, and logging configuration for development environment."
```

### Step 4: Main Application Class
```
"Create the main Spring Boot application class with proper annotations and configuration."
```

## Phase 2: Entity Development

### Step 5: Category Entity
```
"Create a Category JPA entity with:
- id (auto-generated)
- name (required, unique)
- description (optional)
- createdAt and updatedAt timestamps
- proper JPA annotations and validations"
```

### Step 6: Product Entity
```
"Create a Product JPA entity with:
- id (auto-generated)
- name (required)
- description (optional)
- category (many-to-one relationship with Category)
- createdAt and updatedAt timestamps
- proper JPA annotations and validations"
```

### Step 7: SKU Entity
```
"Create a SKU JPA entity with:
- id (auto-generated)
- skuCode (required, unique)
- price (required, positive)
- stockQuantity (required, non-negative)
- attributes (like size, color - store as JSON or separate fields)
- product (many-to-one relationship with Product)
- createdAt and updatedAt timestamps
- proper JPA annotations and validations"
```

### Step 8: Entity Relationships
```
"Add proper bidirectional relationships between Category-Product and Product-SKU entities with cascade operations and fetch types."
```

## Phase 3: Repository Layer

### Step 9: Category Repository
```
"Create CategoryRepository interface extending JpaRepository with custom query methods for finding by name and checking existence."
```

### Step 10: Product Repository
```
"Create ProductRepository interface with custom query methods for:
- Finding products by name containing (case-insensitive search)
- Finding products by category
- Finding products with pagination and sorting
- Combining search and filter functionality"
```

### Step 11: SKU Repository
```
"Create SKURepository interface with methods for:
- Finding SKUs by product
- Finding SKU by skuCode
- Checking SKU existence for a product"
```

## Phase 4: DTO Classes

### Step 12: Category DTOs
```
"Create CategoryRequestDTO and CategoryResponseDTO classes with proper validation annotations."
```

### Step 13: Product DTOs
```
"Create ProductRequestDTO, ProductResponseDTO, and ProductSummaryDTO classes with category information and validation."
```

### Step 14: SKU DTOs
```
"Create SKURequestDTO and SKUResponseDTO classes with product information and validation."
```

### Step 15: Common DTOs
```
"Create PagedResponseDTO for pagination and ErrorResponseDTO for error handling."
```

## Phase 5: Service Layer

### Step 16: Category Service
```
"Create CategoryService class with methods for:
- createCategory(CategoryRequestDTO)
- getAllCategories()
- getCategoryById(Long id)
- updateCategory(Long id, CategoryRequestDTO)
- deleteCategory(Long id)
Include proper validation and exception handling."
```

### Step 17: Product Service
```
"Create ProductService class with methods for:
- createProduct(ProductRequestDTO)
- getAllProducts with pagination
- getProductById(Long id)
- searchProducts(String name, Long categoryId, Pageable pageable)
- updateProduct(Long id, ProductRequestDTO)
- deleteProduct(Long id)
Include category validation and exception handling."
```

### Step 18: SKU Service
```
"Create SKUService class with methods for:
- createSKU(Long productId, SKURequestDTO)
- getSKUsByProductId(Long productId)
- getSKUById(Long id)
- updateSKU(Long id, SKURequestDTO)
- deleteSKU(Long id)
Include product validation and stock management."
```

## Phase 6: Exception Handling

### Step 19: Custom Exceptions
```
"Create custom exception classes:
- ResourceNotFoundException
- ValidationException
- BusinessLogicException
- DuplicateResourceException"
```

### Step 20: Global Exception Handler
```
"Create GlobalExceptionHandler class with @ControllerAdvice to handle all custom exceptions and return proper HTTP responses."
```

## Phase 7: Controller Layer

### Step 21: Category Controller
```
"Create CategoryController REST controller with endpoints for:
- POST /api/categories
- GET /api/categories
- GET /api/categories/{id}
- PUT /api/categories/{id}
- DELETE /api/categories/{id}
Include proper HTTP status codes and validation."
```

### Step 22: Product Controller
```
"Create ProductController REST controller with endpoints for:
- POST /api/products
- GET /api/products (with search, filter, pagination)
- GET /api/products/{id}
- PUT /api/products/{id}
- DELETE /api/products/{id}
Include request parameters for search and filtering."
```

### Step 23: SKU Controller
```
"Create SKUController REST controller with endpoints for:
- POST /api/products/{productId}/skus
- GET /api/products/{productId}/skus
- GET /api/skus/{id}
- PUT /api/skus/{id}
- DELETE /api/skus/{id}
Include proper path variables and validation."
```

## Phase 8: Testing

### Step 24: Service Unit Tests
```
"Create unit tests for CategoryService using Mockito and JUnit 5. Test all CRUD operations and exception scenarios."
```

### Step 25: Product Service Tests
```
"Create comprehensive unit tests for ProductService including search, pagination, and filtering functionality."
```

### Step 26: SKU Service Tests
```
"Create unit tests for SKUService with product relationship validation and stock management scenarios."
```

### Step 27: Integration Tests
```
"Create integration tests for Category REST endpoints using @SpringBootTest and MockMvc."
```

### Step 28: Controller Tests
```
"Create integration tests for Product and SKU controllers with complete request/response validation."
```

### Step 29: Repository Tests
```
"Create repository tests using @DataJpaTest for custom query methods and relationships."
```

## Phase 9: Documentation & Configuration

### Step 30: API Documentation
```
"Add OpenAPI/Swagger configuration and annotations to all REST endpoints for complete API documentation."
```

### Step 31: Validation Enhancement
```
"Review and enhance validation across all layers. Add custom validators for business rules."
```

### Step 32: Logging Configuration
```
"Add proper logging configuration and log statements across service methods."
```

## Phase 10: Final Setup

### Step 33: README Documentation
```
"Create a comprehensive README.md file with:
- Project overview
- Setup instructions
- API endpoints documentation
- Testing instructions
- Technology stack used"
```

### Step 34: Project Structure Documentation
```
"Create Project-structure.md file explaining the architecture, package structure, and design decisions."
```

### Step 35: Test Coverage Report
```
"Configure JaCoCo plugin in pom.xml and generate test coverage report. Show me how to run coverage analysis."
```
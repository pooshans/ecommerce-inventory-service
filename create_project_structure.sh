#!/bin/bash

# Create the folder structure
mkdir -p src/main/java/com/talentica/ecommerce/inventory/{config,controllers,dtos,entities,exceptions,repositories,services}
mkdir -p src/main/resources
mkdir -p src/test/java/com/talentica/ecommerce/inventory
mkdir -p src/test/resources

# Create the main application class
touch src/main/java/com/talentica/ecommerce/inventory/InventoryApplication.java

# Create configuration class
touch src/main/java/com/talentica/ecommerce/inventory/config/AppConfig.java

# Create controller class
touch src/main/java/com/talentica/ecommerce/inventory/controllers/InventoryController.java

# Create DTO class
touch src/main/java/com/talentica/ecommerce/inventory/dtos/ProductDTO.java

# Create entity class
touch src/main/java/com/talentica/ecommerce/inventory/entities/Product.java

# Create exception class
touch src/main/java/com/talentica/ecommerce/inventory/exceptions/ResourceNotFoundException.java

# Create repository interface
touch src/main/java/com/talentica/ecommerce/inventory/repositories/ProductRepository.java

# Create service class
touch src/main/java/com/talentica/ecommerce/inventory/services/InventoryService.java

# Create resource files
touch src/main/resources/application.properties
touch src/main/resources/data.sql
touch src/main/resources/schema.sql

# Create test application class
touch src/test/java/com/talentica/ecommerce/inventory/InventoryApplicationTests.java

# Create test-specific configuration file
touch src/test/resources/application-test.properties

echo "Project structure created successfully!"

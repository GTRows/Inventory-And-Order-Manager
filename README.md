
# Distributor Order System

This project represents a distributor order system that manages products, stock, and orders between main and sub-distributors.

## Project Constraints:

- Products can only be initially defined in the warehouse.
- Only the main distributor can obtain products from the warehouse.
- Sub-distributors specify the desired product and its quantity in the stock to the main distributor when they want products.
- Customers can place orders through all distributors.
- In case of a stock shortage, stock transfers can be made between sub-distributors.
- If a distributor is closed, all product stock will be transferred to the main distributor.

## Getting Started

1. **Prerequisites**: 

   - Java 17
   - Docker
   - MongoDB instance (if not using Docker)


2. **Building the Project**:

   First, ensure you clean the project:

   ```bash
   ./gradlew clean
   ```

   Then, you can build the Docker image:

   ```bash
   docker build -t gtrows/distributorordersystem:latest .
   ```

3. **Running the Project**:

   If you're using Docker, you can simply run:

   ```bash
   docker-compose up
   ```

   This will start the service on port 8080. You can access the Swagger UI at `http://localhost:8080/swagger-ui.html/` to interact with the API.

4. **Database Connection**:

   The project uses MongoDB. By default, it tries to connect to a MongoDB instance at `localhost:27017`. You can change the connection settings in `src/main/resources/application.properties`.


# Distributor Order System API Documentation

## Product API Endpoints:

### CRUD Operations for Product:

- **Create a Product:**
  ```
  POST /api/products
  {
      "productName": "Example Product",
      "description": "This is an example product.",
      "price": 19.99,
      "stockQuantity": 100
  }
  ```

- **Retrieve all Products:**
  ```
  GET /api/products
  ```

- **Retrieve a Product by ID:**
  ```
  GET /api/products/{productId}
  ```

- **Update a Product by ID:**
  ```
  PUT /api/products/{productId}
  {
      "productName": "Updated Product",
      "description": "This is an updated product.",
      "price": 29.99,
      "stockQuantity": 150
  }
  ```

- **Delete a Product by ID:**
  ```
  DELETE /api/products/{productId}
  ```

## Warehouse API Endpoints:

### CRUD Operations for Warehouse:

- **Create a Warehouse:**
  ```
  POST /api/warehouses
  {
      "address": "123 Warehouse St, City, Country"
  }
  ```

- **Retrieve all Warehouses:**
  ```
  GET /api/warehouses
  ```

- **Retrieve a Warehouse by ID:**
  ```
  GET /api/warehouses/{warehouseId}
  ```

- **Update a Warehouse by ID:**
  ```
  PUT /api/warehouses/{warehouseId}
  {
      "address": "456 Updated St, City, Country"
  }
  ```

- **Delete a Warehouse by ID:**
  ```
  DELETE /api/warehouses/{warehouseId}
  ```

- **Add a Product to a Warehouse:**
  ```
  POST /api/warehouses/{warehouseId}/products
  {
      "productId": "01",
      "quantity": 100
  }
  ```

- **Update Stock for a Product in a Warehouse:**
  ```
  PUT /api/warehouses/{warehouseId}/products/{productId}
  {
      "quantity": 50
  }
  ```

- **Remove a Product from a Warehouse:**
  ```
  DELETE /api/warehouses/{warehouseId}/products/{productId}
  ```

## Distributor API Endpoints:

### CRUD Operations for Distributor:

- **Create a Distributor:**
  ```
  POST /api/distributors
  {
      "distributorType": "MAIN",
      "address": "123 Distributor St, City, Country",
      "isActive": true
  }
  ```

- **Retrieve all Distributors:**
  ```
  GET /api/distributors
  ```

- **Retrieve a Distributor by ID:**
  ```
  GET /api/distributors/{distributorId}
  ```

- **Update a Distributor by ID:**
  ```
  PUT /api/distributors/{distributorId}
  {
      "distributorType": "SUB",
      "address": "456 Distributor St, City, Country",
      "isActive": false
  }
  ```

- **Delete a Distributor by ID:**
  ```
  DELETE /api/distributors/{distributorId}
  ```

- **Transfer Product to Distributor from Warehouse (Only for MAIN Distributor):**
  ```
  POST /api/distributors/{distributorId}/transfer
  {
      "productId": "01",
      "quantity": 50
  }
  ```

## Customer API Endpoints:

### CRUD Operations for Customer:

- **Create a Customer:**
  ```
  POST /api/customers
  {
      "name": "John Doe",
      "address": "789 Customer St, City, Country",
      "email": "johndoe@example.com"
  }
  ```

- **Retrieve all Customers:**
  ```
  GET /api/customers
  ```

- **Retrieve a Customer by ID:**
  ```
  GET /api/customers/{customerId}
  ```

- **Update a Customer by ID:**
  ```
  PUT /api/customers/{customerId}
  {
      "name": "Jane Doe",
      "address": "101 Customer St, City, Country",
      "email": "janedoe@example.com"
  }
  ```

- **Delete a Customer by ID:**
  ```
  DELETE /api/customers/{customerId}
  ```

- **Place an Order:**
  ```
  POST /api/customers/{customerId}/orders
  {
      "distributorId": "dist01",
      "productId": "01",
      "quantity": 5
  }
  ```


# Hot Dog Shop API

A backend API for a small hot dog shop built with Kotlin and Spring Boot.

## Features

- **Product Management**: Create, read, update, and delete hot dog menu items
- **Order Processing**: Track orders from creation to delivery
- **Customer Management**: Store customer information for faster repeat orders
- **Inventory Tracking**: Track ingredients and stock levels
- **Sales Reporting**: Generate sales reports and analytics
- **Authentication**: Secure API endpoints with authentication

## Technologies

- Kotlin
- Spring Boot
- Spring Data JPA
- H2 Database
- OpenAPI/Swagger Documentation

## API Documentation

The API documentation is available at `/swagger-ui` when the application is running.

## Database

This project uses H2 in-memory database for development. The H2 console is available at `/h2-console` with the following credentials:

- JDBC URL: `jdbc:h2:mem:hotdogdb`
- Username: `sa`
- Password: `password`

## Getting Started

### Prerequisites

- JDK 11+
- Gradle

### Running the Application

```bash
gradle bootRun
```

## API Endpoints

### Products

- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/search?name={name}` - Search products by name
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update an existing product
- `DELETE /api/products/{id}` - Delete a product
- `PATCH /api/products/{id}/availability` - Toggle product availability

### Customers

- `GET /api/customers` - Get all customers
- `GET /api/customers/{id}` - Get customer by ID
- `GET /api/customers/search?name={name}` - Search customers by name
- `GET /api/customers/email/{email}` - Get customer by email
- `POST /api/customers` - Create a new customer
- `PUT /api/customers/{id}` - Update an existing customer
- `DELETE /api/customers/{id}` - Delete a customer

### Orders

- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/customer/{customerId}` - Get orders by customer ID
- `GET /api/orders/status/{status}` - Get orders by status
- `GET /api/orders/pending` - Get pending orders
- `GET /api/orders/date/{date}` - Get orders for a specific date
- `POST /api/orders` - Create a new order
- `PATCH /api/orders/{id}/status` - Update order status
- `POST /api/orders/{orderId}/items` - Add item to order
- `DELETE /api/orders/{orderId}/items/{itemId}` - Remove item from order
- `PATCH /api/orders/{id}/cancel` - Cancel an order
- `GET /api/orders/sales/{date}` - Get daily sales report
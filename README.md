# 🛒 NexaCart AI – Smart Online Grocery Store

## 📌 Project Overview

NexaCart AI is a web-based online grocery store developed using Java Spring Boot, Spring Data JPA, Hibernate, MySQL, HTML, CSS, JavaScript and Bootstrap.

The application allows customers to register, log in, browse grocery products, add products to a shopping cart, update quantities, place orders and view their order history.

An administrator can manage products, customers and orders through the admin module.

---

## 🎯 Objectives

- Provide an easy-to-use online grocery shopping platform.
- Allow customers to register and securely log in.
- Display grocery products with price, stock and images.
- Provide shopping cart functionality.
- Allow customers to place and manage orders.
- Allow administrators to manage products and orders.
- Store application data using MySQL.
- Demonstrate Java OOP and Spring Boot concepts.

---

## 🚀 Features

### 👤 Customer

- Customer Registration
- Customer Login
- Product Browsing
- Product Details
- Add to Cart
- Update Cart Quantity
- Remove Cart Items
- Checkout
- Place Orders
- View My Orders
- Cancel Eligible Orders

### 👨‍💼 Admin

- Admin Login
- Product Management
- Add Product
- Edit Product
- Delete Product
- Order Management
- Update Order Status
- Customer Management

### 🔐 Security

- BCrypt password hashing
- Password is not exposed in normal API responses
- Product stock validation
- Order validation

---

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| Java | Backend programming |
| Spring Boot | Backend framework |
| Spring Data JPA | Database operations |
| Hibernate | ORM |
| MySQL | Database |
| HTML | Frontend |
| CSS | Styling |
| JavaScript | Frontend functionality |
| Bootstrap | UI design |
| Maven | Build and dependency management |
| Eclipse | Development IDE |

---

## 🏗️ Architecture

```text
User Interface
      ↓
Controller Layer
      ↓
Service Layer
      ↓
Repository Layer
      ↓
MySQL Database

The project follows a layered architecture that separates presentation, business logic and database operations.
📂 Project Structure
OnlineGroceryStore
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── OnlineGroceryStore
│   │   │       ├── controller
│   │   │       ├── entity
│   │   │       ├── repository
│   │   │       └── service
│   │   │
│   │   └── resources
│   │       ├── templates
│   │       ├── static
│   │       │   ├── css
│   │       │   ├── js
│   │       │   └── images
│   │       └── application.properties
│
├── screenshots
├── pom.xml
└── README.md

🗄️ Database
Database name:
grocery_store

Main tables:
category
product
user
cart
cart_item
orders
order_item

▶️ How to Run
1. Clone the repository
git clone https://github.com/YOUR_USERNAME/NexaCart-AI.git

2. Open the project in Eclipse
Import it as a Maven project.
3. Configure MySQL
Create:
grocery_store

Update application.properties with your MySQL username and password.
4. Run the application
Run:
OnlineGroceryStoreApplication.java

5. Open in browser
http://localhost:8080

📸 Application Screenshots
🏠 Home Page

👤 Customer Registration

🔐 Customer Login

🛍️ Products

🛒 Shopping Cart

📦 Orders

👨‍💼 Admin Products

📋 Admin Orders

👥 Admin Customers

🤖 AI Future Scope
The current implementation does not contain a trained AI/ML model.
Future AI enhancements can include:
- Personalized product recommendations
- AI shopping assistant
- Smart product search
- Purchase-based recommendations
- Intelligent grocery suggestions
💳 Payment
The current version does not include an online payment gateway.
Online payment integration can be added in future using gateways such as Razorpay or Stripe.
🔮 Future Scope
- AI-based personalized recommendations
- Online payment integration
- Product search and filtering
- Email/SMS order notifications
- Sales analytics dashboard
- Cloud deployment
- Mobile application

## ⚙️ Jenkins Integration

NexaCart AI was additionally tested using Jenkins to verify the project's build and execution workflow.

### Jenkins Testing
- Configured Jenkins for the project workflow.
- Used Jenkins to execute the configured build process.
- Checked the build result and reviewed the console output.
- Used the results to verify the configured project build.

### Tools Used
- **Jenkins** – CI pipeline and build execution
- **Maven** – Project build and dependency management
- **Java** – Application development
👩‍💻 Developer
Veerbasanna AshwiniJava | Spring Boot | Spring Data JPA | Hibernate | MySQL | HTML5 | CSS3 | JavaScript | Bootstrap | Maven | Eclipse | Embedded Tomcat | BCrypt

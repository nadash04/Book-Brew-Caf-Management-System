# Book-Brew-Caf-Management-System
Book &amp; Brew Café Management System – A JavaFX &amp; MySQL desktop application for managing users, products, orders, and payments in a café environment.

Overview

Book & Brew Café Management System is a Java-based desktop application developed as a final project for a Visual Programming course. The system is designed to streamline café operations and provides role-based access for Visitors, Admins, and Cashiers, allowing secure account management, order processing, and database integration.

Built with JavaFX (GUI designed with Scene Builder), NetBeans IDE, and MySQL (via XAMPP), the project follows MVC architecture with DAO classes for a clean separation of concerns.

 Features

User Roles: Visitor, Admin, Cashier
Authentication: Secure login & account creation with password encryption
Database Integration: MySQL for storing users, products, orders, and payments
GUI: Interactive JavaFX interfaces (login, signup, about, order pages)
Data Validation:Input checks, password confirmation, and error handling
Order Management: Add orders, track items, and generate total prices
Payments:Process and store payment details securely

 Technologies Used

Language: Java
Framework: JavaFX + Scene Builder
IDE: NetBeans
Database:MySQL (XAMPP)
Architecture: MVC + DAO

Database Schema

Main tables:

users Stores account info (user_id, username, password, role, email)
products Contains product details (name, price, quantity, category)
orders Manages customer orders with total price & status
order_itemLinks orders with products
payments Stores payment records
books Keeps book information (title, author, genre, availability)

 How to Run

1. Start XAMPP and enable Apache + MySQL.
2. Import the database `finalprojectdb.sql` into phpMyAdmin.
3. Open the project in NetBeans IDE.
4. Run `FProject.java` to start the application.

 Future Enhancements

 Inventory management for low-stock alerts
 Detailed sales reports and analytics
 Multi-language support
 Online order placement

Developed by Nada, Raneem & Jneen as a final project for the Visual Programming course using JavaFX, Java, and MySQL.


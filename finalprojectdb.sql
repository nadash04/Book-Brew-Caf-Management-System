-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 28, 2025 at 05:03 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
 /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
 /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 /*!40101 SET NAMES utf8mb4 */;

--
-- Database: `finalprojectdb`
--
CREATE DATABASE IF NOT EXISTS `finalprojectdb`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
USE `finalprojectdb`;

-- --------------------------------------------------------
-- Table structure for table `books`
CREATE TABLE `books` (
  `book_id` INT NOT NULL AUTO_INCREMENT,
  `book_genre` VARCHAR(120) NOT NULL,
  `book_title` VARCHAR(120) NOT NULL,
  `book_author` VARCHAR(120) NOT NULL,
  `book_imagepath` TEXT NOT NULL,
  `book_available` TINYINT(1) NOT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Table structure for table `orders`
CREATE TABLE `orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `order_items` TEXT NOT NULL,
  `order_totalPrice` DECIMAL(10,0) NOT NULL,
  `timeStamp` DATETIME NOT NULL,
  `status` TINYINT(1) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_user_order` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Table structure for table `order_item`
CREATE TABLE `order_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `product_name` VARCHAR(100) NOT NULL,
  `quantity` INT NOT NULL,
  `unit_price` DECIMAL(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Table structure for table `payments`
CREATE TABLE `payments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `amount` DOUBLE NOT NULL,
  `status` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Table structure for table `products`
CREATE TABLE `products` (
  `products_id` INT NOT NULL AUTO_INCREMENT,
  `products_name` VARCHAR(120) NOT NULL,
  `products_category` ENUM('Drinks','Snacks','Books','Workspace') NOT NULL,
  `products_price` DECIMAL(10,0) NOT NULL,
  `products_imagepath` VARCHAR(225) NOT NULL,
  `quantity` INT NOT NULL,
  `description` TEXT NOT NULL,
  `available` TINYINT(1) NOT NULL,
  PRIMARY KEY (`products_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table `products`
INSERT INTO `products` (`products_id`, `products_name`, `products_category`, `products_price`, `products_imagepath`, `quantity`, `description`, `available`) VALUES
(143, 'Croissant', 'Snacks', 8, 'images/croissant.jpg', 58, 'Fresh croissant', 0),
(144, 'Meeting Room', 'Workspace', 50, 'images/meeting.jpg', 123, 'Private meeting room', 1),
(145, 'Cheese Sandwich', 'Snacks', 7, 'images/sandwich.jpg', 153, 'Cheese sandwich', 0),
(146, 'Donuts', 'Snacks', 4, 'images/donuts.jpg', 168, 'Fresh donuts', 1),
(148, 'Cappuccino', 'Drinks', 4, 'images/cappuccino.jpg', 150, 'Hot cappuccino', 1),
(149, 'Latte', 'Drinks', 3, 'images/latte.jpg', 56, 'Hot latte', 1),
(154, 'Tea', 'Drinks', 11, 'images/tea.png', 100, 'Tea', 1);

-- --------------------------------------------------------
-- Table structure for table `users`
CREATE TABLE `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(120) NOT NULL,
  `user_password` VARCHAR(120) NOT NULL,
  `first_name` VARCHAR(120) NOT NULL,
  `last_name` VARCHAR(120) NOT NULL,
  `email` VARCHAR(220) NOT NULL,
  `role` ENUM('admin','cashier','visitor','customer') NOT NULL,
  `amount_spent` DECIMAL(10,2) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table `users`
INSERT INTO `users` (`user_id`, `user_name`, `user_password`, `first_name`, `last_name`, `email`, `role`, `amount_spent`) VALUES
(21, 'cashier', 'cashier123', 'Cashier', '', 'cashier@example.com', 'cashier', 0.00),
(22, 'visitor', 'visitor123', 'Visitor', 'User', 'visitor@example.com', 'visitor', NULL),
(23, 'ramy', '1234', 'Raneem', '', 'ran@12345', 'cashier', NULL),
(24, '55', '12345', 'ali', 'ahmed', 'ali@12345', 'admin', NULL),
(25, 'hussein', '12345', 'rana', '', 'rrrr@1234', 'visitor', NULL);

-- Constraints
ALTER TABLE `orders`
  ADD CONSTRAINT `fk_user_order` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `order_item`
  ADD CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`products_id`);

ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
 /*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
 /*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

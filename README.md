# Digital Market Project

The Digital Market project is a simple Java-based digital marketplace that allows buyers, sellers, and administrators to interact. It includes features such as user registration, login, product listing, purchasing, and administrative operations.

## Table of Contents

- [Project Overview](#project-overview)
- [Key Components](#key-components)
- [How to Run](#how-to-run)
- [Implementation Details](#implementation-details)
  - [Structures](#structures)
  - [Main Class](#main-class)
  - [Methods](#methods)
- [License](#license)

## Project Overview

Welcome to the Digital Market! This project aims to provide a basic digital marketplace where users can register, log in, buy and sell products, and administrators can perform administrative tasks.

## Key Components

### 1. Structures

#### Buyer Structure

- **Attributes:**
  - `accountNumber`: Unique identifier for the buyer's account.
  - `name`: Name of the buyer.
  - `password`: Password for buyer authentication.
  - `moneyDeposit`: Amount of money deposited by the buyer.
  - `phoneNumber`: Phone number of the buyer.
  - `emailAddress`: Email address of the buyer.

#### Seller Structure

- **Attributes:**
  - `accountNumber`: Unique identifier for the seller's account.
  - `name`: Name of the seller.
  - `password`: Password for seller authentication.
  - `moneyDeposit`: Amount of money deposited by the seller.
  - `phoneNumber`: Phone number of the seller.
  - `emailAddress`: Email address of the seller.

#### Product Structure

- **Attributes:**
  - `name`: Name of the product.
  - `price`: Price of the product.
  - `type`: Type or category of the product.
  - `totalNumber`: Total quantity of the product available.
  - `sellerPhoneNumber`: Phone number of the seller associated with the product.

### 2. Main Class

The main class is `DigitalMarket`, responsible for the execution of the program. It contains static lists to store instances of buyers, sellers, and products.

### 3. Methods

#### Introduction

- Displays a welcome message introducing the Digital Market.

#### Service Options

- Displays a menu with options such as Service, Terms and Conditions, How to use, About, and Exit.
- Allows users to choose different services or exit the program.

#### Service

- Allows users to select Buyer, Seller, or Admin services.

#### Buyer Service

- Handles buyer-specific functionalities such as signup, login, and product purchase.

#### Seller Service

- Manages seller-specific operations like signup, login, product listing, and new product posting.

#### Admin Service

- Provides administrative access to view buyer, seller, and product data.

#### Open Terms and Conditions

- Displays the terms and conditions of using the Digital Market.

#### Open How to Use

- Displays information on how to use the Digital Market.

#### About Company

- Displays information about the L Digital Market company.

#### Save Buyer/Seller/Product Data

- Saves user and product data to files.

#### Load Buyer/Seller/Product Data

- Loads user and product data from files.

#### Display Product/Buyer/Seller Table

- Displays tables of products, buyers, and sellers.

#### Buy Product

- Allows a buyer to purchase a product.

#### Find Buyer Index

- Finds the index of a buyer based on the account number.

## How to Run

1. Ensure you have Java installed on your machine.
2. Compile the `DigitalMarket.java` file using a Java compiler.
   ```bash
   javac DigitalMarket.java


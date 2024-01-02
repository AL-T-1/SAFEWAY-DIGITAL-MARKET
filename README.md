# Digital Market

Welcome to Digital Market - your go-to platform for buying and selling digital goods.

## Introduction

Digital Market provides a convenient and secure platform for digital transactions, connecting buyers and sellers to thrive in the digital marketplace.

## Features

- Buyer, Seller, and Admin functionalities
- User authentication and login system
- Product posting for sellers
- Product purchase for buyers
- Account number generation and management
- File-based data storage for users and products

## Getting Started

1. Clone the repository:

    ```bash
    git clone <repository-url>
    ```

2. Compile and run the DigitalMarket.java file:

    ```bash
    javac DigitalMarket.java
    java DigitalMarket
    ```

3. Follow the on-screen instructions to navigate through the application.

## Usage

### Main Menu

- **Service**
- **Terms and Conditions**
- **How to use**
- **About**

### Service Menu

#### Buyer Menu

- **Login**
- **Sign up**

#### Seller Menu

- **Login**
- **Sign up**

#### Admin Menu

- **Login**

### Admin Sub-Menu

- **Open Buyers File (Bfile.txt)**
- **Open Sellers File (Sfile.txt)**
- **Open Products File (product.txt)**

## Contributors
SAFEWAY DIGITAL MARKET
GROUP MEMBERS ON THE PROJECT
    1) Abrham Adugna
    2) Abyayle Ababaye
    3) Adonay Kinfe
    4) Aelaf Tsegaye
    5) Alen Wenduwesun

## Code Overview

### User Class

The `User` class is an abstract class representing a generic user. It contains methods for user login, displaying user information, and user signup. The class also includes helper methods for saving user information to a file and generating account numbers.
### Buyer Class
The `Buyer` class extends the `User` class and represents a buyer in the Digital Market. It includes additional methods for purchasing products, updating buyer information, and displaying product information.
### Seller Class

The `Seller` class extends the `User` class and represents a seller in the Digital Market. It includes methods for posting products, updating seller information, and displaying product information.

### Admin Class

The `Admin` class extends the `User` class and represents an admin in the Digital Market. It includes methods for admin login and displaying admin information.

### Product Class

The `Product` class represents a digital product in the Digital Market. It contains information such as name, price, type, total number, and seller's phone number.

### DigitalMarket Class

The `DigitalMarket` class contains the main method that serves as the entry point for the application. It provides a user interface for navigating through the main menu, service menu, and admin sub-menu.

## Acknowledgments

- Thanks to AL-T-1 for the contributions done to the project.

## Contact

For inquiries, please contact AL-T-1.

---

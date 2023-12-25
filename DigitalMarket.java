/* 
SAFEWAY DIGITAL MARKET
GROUP MEMBERS ON THE PROJECT
    1) Abrham Adugna
    2) Abyayle Ababaye
    3) Adonay Kinfe
    4) Aelaf Tsegaye
    5) Alen Wenduwesun
*/
import java.io.*;
import java.util.*;

// Abstract class representing a user
abstract class User {
    protected String accountNumber;
    protected String name;
    protected String password;
    protected double moneyDeposit;
    protected String phoneNumber;
    protected String emailAddress;

    // Abstract method for user login
    // Needs to be done
    abstract boolean login();

    // Abstract method to display user information
    // Needs to be done
    abstract void displayUserInfo();

    // Getter method for account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter method for phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }
}

// Buyer class extending User
class Buyer extends User {
    // Constructor for Buyer
    public Buyer(String accountNumber, String name, String password, double moneyDeposit,
                 String phoneNumber, String emailAddress) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.password = password;
        this.moneyDeposit = moneyDeposit;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    // Implementation of abstract method for buyer login
    @Override
    boolean login() {
        // Implement buyer login logic
        // Check validity from Bfile.txt
        // Return true if login is successful, false otherwise
        return true; // Placeholder, actual logic needed
    }

    // Implementation of abstract method to display buyer information
    @Override
    void displayUserInfo() {
        // Implement code to display buyer information
        System.out.println("Buyer Information:");
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Money Deposit: $" + moneyDeposit);
    }
}

// Seller class extending User
class Seller extends User {
    // Constructor for Seller
    public Seller(String accountNumber, String name, String password, double moneyDeposit,
                  String phoneNumber, String emailAddress) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.password = password;
        this.moneyDeposit = moneyDeposit;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    // Implementation of abstract method for seller login
    @Override
    boolean login() {
        // Implement seller login logic
        // Check validity from Sfile.txt
        // Return true if login is successful, false otherwise
        return true; // Placeholder, actual logic needed
    }

    // Implementation of abstract method to display seller information
    @Override
    void displayUserInfo() {
        // Implement code to display seller information
        System.out.println("Seller Information:");
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Money Deposit: $" + moneyDeposit);
    }
}

// Admin class extending User
class Admin extends User {
    // Constants for admin account
    private static final String ADMIN_ACCOUNT_NUMBER = "0000";
    private static final String ADMIN_PASSWORD = "password";

    // Constructor for Admin
    public Admin() {
        // Set admin account information
        this.accountNumber = ADMIN_ACCOUNT_NUMBER;
        this.password = ADMIN_PASSWORD;
    }

    // Implementation of abstract method for admin login
    @Override
    boolean login() {
        // Implement admin login logic
        // Check validity using hardcoded admin credentials
        return true; // Placeholder, actual logic needed
    }

    // Implementation of abstract method to display admin information
    @Override
    void displayUserInfo() {
        // Implement code to display admin information
        System.out.println("Admin Information:");
        System.out.println("Account Number: " + accountNumber);
    }
}

// Product class representing a product
class Product {
    private String name;
    private double price;
    private String type;
    private int totalNumber;
    private String sellerPhoneNumber;

    // Constructor for Product
    public Product(String name, double price, String type, int totalNumber, String sellerPhoneNumber) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.totalNumber = totalNumber;
        this.sellerPhoneNumber = sellerPhoneNumber;
    }

    // Getter method for product name
    public String getName() {
        return name;
    }

    // Getter method for product price
    public double getPrice() {
        return price;
    }

    // Getter method for product type
    public String getType() {
        return type;
    }

    // Getter method for total number of products
    public int getTotalNumber() {
        return totalNumber;
    }

    // Getter method for seller's phone number
    public String getSellerPhoneNumber() {
        return sellerPhoneNumber;
    }

    // Method to display product information
    public void displayProductInfo() {
        // Implement code to display product information
        System.out.println("Product Information:");
        System.out.println("Name: " + name);
        System.out.println("Price: $" + price);
        System.out.println("Type: " + type);
        System.out.println("Total Number: " + totalNumber);
        System.out.println("Seller's Phone Number: " + sellerPhoneNumber);
    }
}

// DigitalMarket class representing the main program
public class DigitalMarket {
    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);

    // List to store products
    // private static List<Product> products = new ArrayList<>();

    // Main method
    public static void main(String[] args) {
        // Display introduction about the company
        System.out.println("Welcome to Digital Market!");
        System.out.println("Your go-to platform for buying and selling digital goods.");

        // Main menu loop
        while (true) {
            // Display main menu options
            System.out.println("\nMain Menu:");
            System.out.println("1. Service");
            System.out.println("2. Terms and Conditions");
            System.out.println("3. How to use");
            System.out.println("4. About");

            // Get user choice
            int choice = getChoice(1, 4);

            // Perform actions based on user choice
            switch (choice) {
                case 1:
                    serviceMenu();
                    break;
                case 2:
                    displayFileContent("term.txt");
                    break;
                case 3:
                    displayFileContent("help.txt");
                    break;
                case 4:
                    displayAboutInfo();
                    break;
            }
        }
    }

    // Method to display service menu options
    private static void serviceMenu() {
        while (true) {
            // Display service menu options
            System.out.println("\nService Menu:");
            System.out.println("1. Buyer");
            System.out.println("2. Seller");
            System.out.println("3. Admin");

            // Get user choice
            int choice = getChoice(1, 3);

            // Perform actions based on user choice
            switch (choice) {
                case 1:
                    buyerMenu();
                    break;
                case 2:
                    sellerMenu();
                    break;
                case 3:
                    adminMenu();
                    break;
            }
        }
    }

    // Method to handle buyer-related actions
    private static void buyerMenu() {
        // Display buyer login/signup options
        System.out.println("\nBuyer Menu:");
        System.out.println("1. Login");
        System.out.println("2. Sign up");

        // Get user choice
        int choice = getChoice(1, 2);

        // Perform actions based on user choice
        switch (choice) {
            case 1:
                // Implement buyer login logic
                // Check validity from Bfile.txt
                // Return true if login is successful, false otherwise
                boolean loginSuccess = true; // Placeholder, actual logic needed

                if (loginSuccess) {
                    // Display buyer information
                    // Display products available for purchase
                    // Allow buyer to buy products
                    // Update product.txt and Bfile.txt accordingly
                } else {
                    System.out.println("Invalid login. Please try again.");
                }
                break;
            case 2:
                // Implement buyer sign up logic
                // Get input for account number, name, password, money deposit, phone number, and email address
                // Save input to Bfile.txt
                break;
        }
    }

    // Method to handle seller-related actions
    private static void sellerMenu() {
        // Display seller login/signup options
        System.out.println("\nSeller Menu:");
        System.out.println("1. Login");
        System.out.println("2. Sign up");

        // Get user choice
        int choice = getChoice(1, 2);

        // Perform actions based on user choice
        switch (choice) {
            case 1:
                // Implement seller login logic
                // Check validity from Sfile.txt
                // Return true if login is successful, false otherwise
                boolean loginSuccess = true; // Placeholder, actual logic needed

                if (loginSuccess) {
                    // Display seller information
                    // Display products posted by the seller
                    // Allow seller to post new products
                    // Update product.txt accordingly
                } else {
                    System.out.println("Invalid login. Please try again.");
                }
                break;
            case 2:
                // Implement seller sign up logic
                // Get input for account number, name, password, money deposit, phone number, and email address
                // Save input to Sfile.txt
                break;
        }
    }

    // Method to handle admin-related actions
    private static void adminMenu() {
        // Display admin login option
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Login");

        // Get user choice
        int choice = getChoice(1, 1);

        // Perform actions based on user choice
        switch (choice) {
            case 1:
                // Implement admin login logic
                // Check validity using hardcoded admin credentials
                boolean adminLoginSuccess = true; // Placeholder, actual logic needed

                if (adminLoginSuccess) {
                    adminSubMenu();
                } else {
                    System.out.println("Invalid login. Please try again.");
                }
                break;
        }
    }

    // Method to handle admin sub-menu actions
    private static void adminSubMenu() {
        while (true) {
            // Display admin sub-menu options
            System.out.println("\nAdmin Sub-Menu:");
            System.out.println("1. Open Buyers File (Bfile.txt)");
            System.out.println("2. Open Sellers File (Sfile.txt)");
            System.out.println("3. Open Products File (product.txt)");

            // Get user choice
            int choice = getChoice(1, 3);

            // Perform actions based on user choice
            switch (choice) {
                case 1:
                    // Implement code to open and display Bfile.txt
                    // Ask if the user wants to terminate the program
                    // If yes, exit the program; if no, continue admin sub-menu
                    break;
                case 2:
                    // Implement code to open and display Sfile.txt
                    // Ask if the user wants to terminate the program
                    // If yes, exit the program; if no, continue admin sub-menu
                    break;
                case 3:
                    // Implement code to open and display product.txt
                    // Ask if the user wants to terminate the program
                    // If yes, exit the program; if no, continue admin sub-menu
                    break;
            }
        }
    }

    // Method to get user choice within a specified range
    private static int getChoice(int min, int max) {
        int choice = 0;
        while (true) {
            try {
                System.out.print("Enter your choice (" + min + "-" + max + "): ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return choice;
    }

    // Method to display the content of a text file
    private static void displayFileContent(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }

    // Method to display information about the company
    private static void displayAboutInfo() {
        // Implement code to display information about the company
        System.out.println("Digital Market - Your go-to platform for buying and selling digital goods.");
        System.out.println("Main Aim: Provide a convenient and secure platform for digital transactions.");
        System.out.println("Motto: Connect, Transact, Thrive");
    }
}

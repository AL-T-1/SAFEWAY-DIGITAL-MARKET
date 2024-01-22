/* 
SAFEWAY DIGITAL MARKET
GROUP MEMBERS ON THE PROJECT
    1) Abrham Adugna
    2) Abyayel Abebaye
    3) Adonay Kinfe
    4) Aelaf Tsegaye
    5) Alen Wenduwesun
*/
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.nio.file.*;


abstract class User {
    protected String accountNumber;
    protected String name;
    protected String password;
    protected double moneyDeposit;
    protected String phoneNumber;
    protected String emailAddress;

    abstract boolean login();

    abstract void displayUserInfo();

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    abstract void signUp();

    // Helper method to save user information to a file
    protected void saveUserToFile(String fileName) {
        try {
            String userInfo = accountNumber + "," + name + "," +
                    password + "," + moneyDeposit + "," +
                    phoneNumber + "," + emailAddress;

            Files.write(Paths.get(fileName), (userInfo + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("An error occurred while saving user information: " + e.getMessage());
        }
    }

    // Helper method to generate an account number by incrementing from the last added account number in the file
    protected String generateAccountNumber(String fileName) {
        String lastAccountNumber = getLastAccountNumber(fileName);

        if (lastAccountNumber != null) {
            int incrementedNumber = Integer.parseInt(lastAccountNumber) + 1;
            return String.format("%06d", incrementedNumber); // Pad with zeros if needed
        } else {
            return String.valueOf((int) (Math.random() * 900000) + 100000);
        }
    }

    // Helper method to get the last added account number from a file
    private String getLastAccountNumber(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            if (!lines.isEmpty()) {
                String lastLine = lines.get(lines.size() - 1);
                String[] parts = lastLine.split(",");
                if (parts.length > 0) {
                    return parts[0].trim();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return null;
    }
}

class Buyer extends User {
    public Buyer(String accountNumber, String name, String password, double moneyDeposit,
                 String phoneNumber, String emailAddress) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.password = password;
        this.moneyDeposit = moneyDeposit;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    @Override
    boolean login() {
        System.out.println("Buyer Login:");
        String enteredAccountNumber = getInput("Enter your six-digit account number: ");
        String enteredPassword = getInput("Enter your password: ");

        if (checkBuyerCredentials(enteredAccountNumber, enteredPassword)) {
            System.out.println("Login successful!");

            return true;
        } else {
            System.out.println("Invalid login. Please try again.");
            return false;
        }
    }

    @Override
    void displayUserInfo() {
        System.out.println("Buyer Information:");
        System.out.print("Name: " + name+"\t\t\t\t\t\t\t");
        System.out.println("Account Number: " + accountNumber);
        System.out.print("Phone Number: " + phoneNumber+"\t\t\t\t\t");
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Money Deposit: $" + moneyDeposit);
    }

    private boolean checkBuyerCredentials(String enteredAccountNumber, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Bfile.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String accountNumberFromFile = parts[0].trim();
                    String passwordFromFile = parts[2].trim();

                    if (accountNumberFromFile.equals(enteredAccountNumber) && passwordFromFile.equals(enteredPassword)) {
                        setUserDetails(parts);
                        return true;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return false;
    }

    private void setUserDetails(String[] parts) {
        this.accountNumber = parts[0].trim();
        this.name = parts[1].trim();
        this.password = parts[2].trim();
        this.moneyDeposit = Double.parseDouble(parts[3].trim());
        this.phoneNumber = parts[4].trim();
        this.emailAddress = parts[5].trim();
    }

    private String getInput(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
    @Override
    void signUp() {
        System.out.println("Buyer Sign Up:");

        accountNumber = generateAccountNumber("Bfile.txt");
        name = getInput("Enter your name: ");
        password = getInput("Create a password: ");
        moneyDeposit = getDepositAmount();
        phoneNumber = getInput("Enter your phone number: ");
        emailAddress = getInput("Enter your email address: ");

        saveUserToFile("Bfile.txt");

        System.out.println("Buyer account created successfully! Your account number is"+accountNumber);
    }
    private double getDepositAmount() {
        double deposit = 0;
        while (true) {
            try {
                System.out.print("Enter the initial deposit amount: $");
                Scanner scanner = new Scanner(System.in);
                deposit = Double.parseDouble(scanner.nextLine());
                if (deposit >= 0) {
                    break;
                } else {
                    System.out.println("Invalid amount. Please enter a non-negative value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return deposit;
    }
    void purchaseProduct() {
        int entry = getAmountInput("What would you like to purchase?\n1. Electronics\n2. Clothing\n3. Vehicle\n4. Education Materials ");
        switch (entry){
            case 1:
                getProductByType("Electronics");
                break;
            case 2:
                getProductByType("Clothing");
                break;
            case 3:
                getProductByType("Vehicle");
                break;
            case 4:
                getProductByType("Education");
                break;
        }
       // displayProductFile();

        // Get user input for the product to purchase
        String productName = getInput("Enter the name of the product you want to purchase: ");
        int quantity = getAmountInput("Enter the quantity to purchase: ");

        // Get the selected product from the product file
        Product selectedProduct = getProductByName(productName);

        if (selectedProduct != null) {
            double totalPrice = selectedProduct.getPrice() * quantity;

            if (totalPrice <= getMoneyDeposit()) {
                // Deduct the total price from the buyer's money deposit
                setMoneyDeposit(getMoneyDeposit() - totalPrice);

                // Update buyer information in Bfile.txt
                updateBuyerInfo(this);

                // Update seller information in Sfile.txt
                updateSellerInfo(selectedProduct.getSellerPhoneNumber(), totalPrice);

                // Update product information in product.txt
                updateProductInfo(selectedProduct, quantity);

                System.out.println("Purchase successful!");
                System.out.println("Total Cost: $" + totalPrice);
            } else {
                System.out.println("Insufficient funds. Please deposit more money or choose fewer items.");
            }
        } else {
            System.out.println("Product not found. Please check the product name and try again.");
        }
    }

    private void displayProductFile() {
        System.out.printf("---------------------------------------------------------------------------------------------------\n");
        System.out.printf("                                          PRODUCT LIST\n");
        System.out.printf("---------------------------------------------------------------------------------------------------\n");
        System.out.printf("|%-25s |%-10s| %-26s|%10s|%20s|\n","   Name","   Price","     Category","Quantity ","Phone No     ");
        System.out.printf("---------------------------------------------------------------------------------------------------\n");
        try {
            List<String> lines = Files.readAllLines(Paths.get("product.txt"));
            for (String line : lines) {



                String[] substrings = line.split(",");
                System.out.printf("|%-25s |%-10s| %-26s|%10s|%20s|\n","   "+substrings[0],"   "+substrings[1],"     "+substrings[2],substrings[3]+" ",substrings[4]+"    ");
                System.out.printf("---------------------------------------------------------------------------------------------------\n");


            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the product file: " + e.getMessage());
        }
    }

    private Product getProductByName(String productName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("product.txt"));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String nameFromFile = parts[0].trim();
                    if (nameFromFile.equals(productName)) {
                        return new Product(parts[0].trim(), Double.parseDouble(parts[1].trim()),
                                parts[2].trim(), Integer.parseInt(parts[3].trim()), parts[4].trim());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the product file: " + e.getMessage());
        }
        return null;
    }

    private Product getProductByType(String productType) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("product.txt"));
                System.out.printf("---------------------------------------------------------------------------------------------------\n");
                System.out.printf("                                          PRODUCT LIST\n");
                System.out.printf("---------------------------------------------------------------------------------------------------\n");
                System.out.printf("|%-25s |%-10s| %-26s|%10s|%20s|\n","   Name","   Price","     Category","Quantity ","Phone No     ");
                System.out.printf("---------------------------------------------------------------------------------------------------\n");

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String typeFromFile = parts[2].trim();
                    if (typeFromFile.equals(productType)) {
                        System.out.printf("|%-25s |%-10s| %-26s|%10s|%20s|\n","   "+parts[0],"   "+parts[1],"     "+parts[2],parts[3]+" ",parts[4]+"    ");
                        System.out.printf("---------------------------------------------------------------------------------------------------\n");

                        /*return new Product(parts[0].trim(), Double.parseDouble(parts[1].trim()),
                                parts[2].trim(), Integer.parseInt(parts[3].trim()), parts[4].trim());*/
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the product file: " + e.getMessage());
        }
        return null;
    }

    private void updateProductInfo(Product product, int quantity) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("product.txt"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String nameFromFile = parts[0].trim();
                    if (nameFromFile.equals(product.getName())) {
                        int currentTotalNumber = Integer.parseInt(parts[3].trim());
                        int updatedTotalNumber = currentTotalNumber - quantity;
                        parts[3] = String.valueOf(updatedTotalNumber);
                    }
                }
                updatedLines.add(String.join(",", parts));
            }

            Files.write(Paths.get("product.txt"), updatedLines);
        } catch (IOException e) {
            System.out.println("An error occurred while updating product information: " + e.getMessage());
        }
    }
    private int getAmountInput(String prompt) {
        int amount = 0;
        while (true) {
            try {
                System.out.print(prompt);
                Scanner scanner = new Scanner(System.in);
                amount = Integer.parseInt(scanner.nextLine());
                if (amount >= 0) {
                    break;
                } else {
                    System.out.println("Invalid amount. Please enter a non-negative value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return amount;
    }

    private void updateSellerInfo(String sellerPhoneNumber, double totalPrice) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("Sfile.txt"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String phoneNumberFromFile = parts[4].trim();
                    if (phoneNumberFromFile.equals(sellerPhoneNumber)) {
                        double currentMoneyDeposit = Double.parseDouble(parts[3].trim());
                        double updatedMoneyDeposit = currentMoneyDeposit + totalPrice;
                        parts[3] = String.valueOf(updatedMoneyDeposit);
                    }
                }
                updatedLines.add(String.join(",", parts));
            }

            Files.write(Paths.get("Sfile.txt"), updatedLines);
        } catch (IOException e) {
            System.out.println("An error occurred while updating seller information: " + e.getMessage());
        }
    }

    private void updateBuyerInfo(Buyer buyer) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("Bfile.txt"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String accountNumberFromFile = parts[0].trim();
                    if (accountNumberFromFile.equals(buyer.getAccountNumber())) {
                        parts[3] = String.valueOf(buyer.getMoneyDeposit());
                    }
                }
                updatedLines.add(String.join(",", parts));
            }

            Files.write(Paths.get("Bfile.txt"), updatedLines);
        } catch (IOException e) {
            System.out.println("An error occurred while updating buyer information: " + e.getMessage());
        }
    }
    public double getMoneyDeposit() {
        return moneyDeposit;
    }
    public void setMoneyDeposit(double moneyDeposit) {
        this.moneyDeposit = moneyDeposit;
    }

}

class Seller extends User {
    public Seller(String accountNumber, String name, String password, double moneyDeposit,
                  String phoneNumber, String emailAddress) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.password = password;
        this.moneyDeposit = moneyDeposit;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    @Override
    boolean login() {
        System.out.println("Seller Login:");
        String enteredAccountNumber = getInput("Enter your six-digit account number: ");
        String enteredPassword = getInput("Enter your password: ");

        if (checkSellerCredentials(enteredAccountNumber, enteredPassword)) {
            System.out.println("Login successful!");

            return true;
        } else {
            System.out.println("Invalid login. Please try again.");
            return false;
        }
    }

    @Override
    void displayUserInfo() {
        System.out.println("Seller Information:");
        System.out.print("Name: " + name+"\t\t\t\t\t\t\t");
        System.out.println("Account Number: " + accountNumber);
        System.out.print("Phone Number: " + phoneNumber+"\t\t\t\t\t");
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Money Deposit: $" + moneyDeposit);
    }

    private boolean checkSellerCredentials(String enteredAccountNumber, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Sfile.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String accountNumberFromFile = parts[0].trim();
                    String passwordFromFile = parts[2].trim();

                    if (accountNumberFromFile.equals(enteredAccountNumber) && passwordFromFile.equals(enteredPassword)) {
                        setUserDetails(parts);
                        return true;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return false;
    }

    private void setUserDetails(String[] parts) {
        this.accountNumber = parts[0].trim();
        this.name = parts[1].trim();
        this.password = parts[2].trim();
        this.moneyDeposit = Double.parseDouble(parts[3].trim());
        this.phoneNumber = parts[4].trim();
        this.emailAddress = parts[5].trim();
    }

    private String getInput(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
    @Override
    void signUp() {
        System.out.println("Seller Sign Up:");

        accountNumber = generateAccountNumber("Sfile.txt");
        name = getInput("Enter your name: ");
        password = getInput("Create a password: ");
        moneyDeposit = getDepositAmount();
        phoneNumber = getInput("Enter your phone number: ");
        emailAddress = getInput("Enter your email address: ");

        saveUserToFile("Sfile.txt");

        System.out.println("Seller account created successfully!Your account number is"+accountNumber);
    }
    private double getDepositAmount() {
        double deposit = 0;
        while (true) {
            try {
                System.out.print("Enter the initial deposit amount: $");
                Scanner scanner = new Scanner(System.in);
                deposit = Double.parseDouble(scanner.nextLine());
                if (deposit >= 0) {
                    break;
                } else {
                    System.out.println("Invalid amount. Please enter a non-negative value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return deposit;
    }
    void postProduct() {
        System.out.println("Product Posting:");

        String productName = getInput("Enter the product name: ");
        double productPrice = getDoubleInput("Enter the product price: $");
        String productType = getInput("Enter the product type: ");
        int productAmount = getAmountInput("Enter the product amount: ");

        Product newProduct = new Product(productName, productPrice, productType, productAmount, phoneNumber);

        // Save new product information to product.txt
        saveProductInfo(newProduct);

        System.out.println("Product posted successfully!");
    }

    private int getAmountInput(String prompt) {
        int amount = 0;
        while (true) {
            try {
                System.out.print(prompt);
                Scanner scanner = new Scanner(System.in);
                amount = Integer.parseInt(scanner.nextLine());
                if (amount >= 0) {
                    break;
                } else {
                    System.out.println("Invalid amount. Please enter a non-negative value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return amount;
    }

    private void saveProductInfo(Product product) {
        try {
            String productInfo = product.getName() + "," + product.getPrice() + "," +
                    product.getType() + "," + product.getTotalNumber() + "," +
                    product.getSellerPhoneNumber();

            Files.write(Paths.get("product.txt"), (productInfo + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("An error occurred while saving product information: " + e.getMessage());
        }
    }

    // (existing code)

    private double getDoubleInput(String prompt) {
        double value = 0.0;
        while (true) {
            try {
                System.out.print(prompt);
                Scanner scanner = new Scanner(System.in);
                value = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }
}

class Admin extends User {
    private static final String ADMIN_ACCOUNT_NUMBER = "0000";
    private static final String ADMIN_PASSWORD = "password";

    public Admin() {
        this.accountNumber = ADMIN_ACCOUNT_NUMBER;
        this.password = ADMIN_PASSWORD;
    }

    @Override
    boolean login() {
        System.out.println("Admin Login:");
        String enteredPassword = getInput("Enter the admin password: ");

        if (enteredPassword.equals(ADMIN_PASSWORD)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid login. Please try again.");
            return false;
        }
    }

    @Override
    void displayUserInfo() {
        System.out.println("Admin Information:");
        System.out.println("Account Number: " + accountNumber);
    }

    private String getInput(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
    @Override
    void signUp(){}
}

class Product {
    private String name;
    private double price;
    private String type;
    private int totalNumber;
    private String sellerPhoneNumber;

    public Product(String name, double price, String type, int totalNumber, String sellerPhoneNumber) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.totalNumber = totalNumber;
        this.sellerPhoneNumber = sellerPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public String getSellerPhoneNumber() {
        return sellerPhoneNumber;
    }

    public void displayProductInfo() {
        System.out.println("Product Information:");
        System.out.println("Name: " + name);
        System.out.println("Price: $" + price);
        System.out.println("Type: " + type);
        System.out.println("Total Number: " + totalNumber);
        System.out.println("Seller's Phone Number: " + sellerPhoneNumber);
    }
}

public class DigitalMarket {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Digital Market!");
        System.out.println("Your go-to platform for buying and selling digital goods.");

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Service");
            System.out.println("2. Terms and Conditions");
            System.out.println("3. How to use");
            System.out.println("4. About");

            int choice = getChoice(1, 4);

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

    private static void serviceMenu() {
        while (true) {
            System.out.println("\nService Menu:");
            System.out.println("1. Buyer");
            System.out.println("2. Seller");
            System.out.println("3. Admin");

            int choice = getChoice(1, 3);

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

    private static void buyerMenu() {
        System.out.println("\nBuyer Menu:");
        System.out.println("1. Login");
        System.out.println("2. Sign up");

        int choice = getChoice(1, 2);

        switch (choice) {
            case 1:
                Buyer buyer = new Buyer("", "", "", 0.0, "", "");
                if (buyer.login()) {
                    buyer.displayUserInfo();
                    buyer.purchaseProduct();
                    // Additional buyer actions
                }
                break;
            case 2:
                Buyer buyers = new Buyer(null, null, null, choice, null, null);
                buyers.signUp();
                break;
        }
    }

    private static void sellerMenu() {
        System.out.println("\nSeller Menu:");
        System.out.println("1. Login");
        System.out.println("2. Sign up");

        int choice = getChoice(1, 2);

        switch (choice) {
            case 1:
                Seller seller = new Seller("", "", "", 0.0, "", "");
                if (seller.login()) {
                    seller.displayUserInfo();
                    seller.postProduct();
                    // Additional seller actions
                }
                break;
            case 2:
                Seller sellers = new Seller(null, null, null, choice, null, null);
                sellers.signUp();
                break;
        }
    }
    private static void adminMenu() {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Login");

        int choice = getChoice(1, 1);

        switch (choice) {
            case 1:
                Admin admin = new Admin();
                if (admin.login()) {
                    adminSubMenu();
                }
                break;
        }
    }
    private static void adminSubMenu() {
        while (true) {
            System.out.println("\nAdmin Sub-Menu:");
            System.out.println("1. Open Buyers File (Bfile.txt)");
            System.out.println("2. Open Sellers File (Sfile.txt)");
            System.out.println("3. Open Products File (product.txt)");

            int choice = getChoice(1, 3);

            switch (choice) {
                case 1:
                    displayUFileContent("Bfile.txt","BUYER");
                    break;
                case 2:
                    displayUFileContent("Sfile.txt","SELLER");
                    break;
                case 3:
                    displayPFileContent("product.txt");
                    break;
            }
        }
    }
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
    private static void displayUFileContent(String fileName, String us) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.printf("                                          "+us+ " LIST\n");
            System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.printf("|%-10s |%-40s| %-20s|%10s|%20s|%-30s|\n","Account No","   Name","   Password","Balance","Phone No     ","     Email");
            while ((line = reader.readLine()) != null) {
                String[] substrings = line.split(",");
                System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------\n");
                System.out.printf("|%-10s |%-40s| %-20s|%10s|%20s|%-30s|\n"," "+substrings[0],"  "+substrings[1],substrings[2],substrings[3],substrings[4]+" ","     "+substrings[5]);
                System.out.printf("-------------------------------------------------------------------------------------------------------------------------------------------\n");

            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
    private static void displayPFileContent(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;


            System.out.printf("---------------------------------------------------------------------------------------------------\n");
            System.out.printf("                                          PRODUCT LIST\n");
            System.out.printf("---------------------------------------------------------------------------------------------------\n");
            System.out.printf("|%-25s |%-10s| %-26s|%10s|%20s|\n","   Name","   Price","     Category","Quantity ","Phone No     ");
            System.out.printf("---------------------------------------------------------------------------------------------------\n");
            while ((line = reader.readLine()) != null) {
                String[] substrings = line.split(",");
                System.out.printf("|%-25s |%-10s| %-26s|%10s|%20s|\n","   "+substrings[0],"   "+substrings[1],"     "+substrings[2],substrings[3]+" ",substrings[4]+"    ");
                System.out.printf("---------------------------------------------------------------------------------------------------\n");

            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
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
    private static void displayAboutInfo() {
        System.out.println("Digital Market - Your go-to platform for buying and selling digital goods.");
        System.out.println("Main Aim: Provide a convenient and secure platform for digital transactions.");
        System.out.println("Motto: Connect, Transact, Thrive");
    }
}
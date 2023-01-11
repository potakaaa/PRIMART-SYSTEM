import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PriMart {
    // Declare final variables for SQL connection
    static final String databaseURL = "jdbc:mysql://localhost:3306/primart-database";
    static final String username = "root";
    static final String password = "toor";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        Scanner sc3 = new Scanner(System.in);

        // Declare Account Variables to avoid error when accessing
        String firstName = ""; String lastName = ""; String accountName = "";
        System.out.println();
        System.out.println("               PriMart");
        System.out.println("    Add to Cart? Shop in PriMart!");
        System.out.println("       ----------------------");
        System.out.println("       | CODE |    OPTION   |");
        System.out.println("       ----------------------");
        System.out.println("       |  SU  |    SIGNUP   |");
        System.out.println("       |  LN  |    LOGIN    |");
        System.out.println("       ----------------------");
        System.out.println();

        // Loops when Signup or Login choice is invalid
        boolean boolSignupOrLogin = false;
        while(!boolSignupOrLogin) {
            System.out.print("SIGNUP OR LOGIN? ");
            String loginOrSignup = sc.nextLine().toUpperCase();


            if (loginOrSignup.equals("SU")) {
                boolSignupOrLogin = true;
                try {
                    Connection con = DriverManager.getConnection(databaseURL, username, password);
                    Statement st = con.createStatement();
                    System.out.println();

                    // Declare variables needed for new account
                    String newFirstName, newLastName, newMobNumber, newUsername, newPassword;
                    newFirstName = "";
                    newLastName = "";
                    newMobNumber = "";
                    newUsername = "";
                    newPassword = "";
                    boolean boolFirstName = false;

                    // Loops when first name is invalid
                    while (!boolFirstName) {
                        System.out.print("Enter First Name: ");
                        newFirstName = sc.nextLine();

                        // Loops when first name has numbers and special characters
                        if (newFirstName.matches(".*\\d.*")) {
                            System.out.println("ERROR: First Name should not contain numerical characters!");
                            System.out.println();
                        } else {
                            boolFirstName = true;
                        }
                    }

                    // Loops when last name is invalid
                    boolean boolLastName = false;
                    while (!boolLastName) {
                        System.out.print("Enter Last Name: ");
                        newLastName = sc.nextLine();

                        // Loops when last name has numbers and special characters
                        if (newLastName.matches(".*\\d.*")) {
                            System.out.println("ERROR: Last Name should not contain numerical characters!");
                            System.out.println();
                        } else {
                            boolLastName = true;
                        }
                    }
                    accountName = newFirstName.toUpperCase() + " " + newLastName.toUpperCase();

                    // Loops when mobile number is invalid
                    boolean boolMobNumber = false;
                    while (!boolMobNumber) {
                        System.out.print("Enter Mobile Number: ");
                        newMobNumber = sc.nextLine();

                        // Loops when mobile number contains letters
                        if (newMobNumber.matches(".*[a-zA-Z].*")) {
                            System.out.println("ERROR: Mobile Number should not contain alphabetical characters!");
                            System.out.println();
                        } else if (newMobNumber.length() == 11) {
                            boolMobNumber = true;
                        } else {
                            System.out.println("ERROR: +63 Mobile Number should contain 11 numerical characters!");
                            System.out.println();
                        }
                    }

                    // Loops when username is invalid
                    boolean boolUsername = false;
                    while (!boolUsername) {
                        System.out.print("Enter Username: ");
                        newUsername = sc.nextLine();

                        // Loops if username is lesser than 5
                        if (newUsername.length() < 5) {
                            System.out.println("ERROR: Username should contain atleast 5 characters!");
                            System.out.println();
                        } else {
                            boolUsername = true;
                        }
                    }

                    // Loops when password doesn't contain special characters or numbers and length is lesser than 8
                    boolean boolPassword = false;
                    while (!boolPassword) {
                        System.out.print("Enter Password: ");
                        newPassword = sc.nextLine();
                        if (newPassword.matches(".*\\d.*") && newPassword.length() > 8) {
                            boolPassword = true;
                        } else {
                            System.out.println("ERROR: Password should contain atleast 8 alphanumerical characters!");
                            System.out.println();
                        }
                    }

                    int accountId = 1;
                    String createAccount = "INSERT INTO account_database VALUES(\"" + accountId + "\",\"" + newFirstName.toUpperCase() + "\"," +
                            "\"" + newLastName.toUpperCase() + "\",\"" + newMobNumber + "\", \"" + newUsername + "\",\"" + newPassword + "\")";
                    int val = st.executeUpdate(createAccount);

                    // Loading effect
                    try {
                        System.out.println();
                        System.out.print("Creating Account");
                        for (int i = 0; i < 3; i++) {
                            Thread.sleep(500);
                            System.out.print(".");
                        }
                    } catch (InterruptedException e) {
                        System.out.println("ERROR");
                        e.printStackTrace();
                    }
                    System.out.println();
                    System.out.println("Account Successfuly Created!");


                } catch (SQLException e) {
                    System.out.println("SERVER ERROR!");
                    e.printStackTrace();
                }
            } else if (loginOrSignup.equals("LN")) {
                boolSignupOrLogin = true;
                try {
                    Connection con = DriverManager.getConnection(databaseURL, username, password);
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("select * from account_database");

                    // Create arrays of account info to store variables from database later
                    String[] usernameList = new String[100];
                    String[] passwordList = new String[100];
                    String[] firstNameList = new String[100];
                    String[] lastNameList = new String[100];

                    // Stores into variables from current database
                    int id = 0;
                    while (rs.next()) {
                        usernameList[id] = rs.getString("username");
                        passwordList[id] = rs.getString("password");
                        firstNameList[id] = rs.getString("firstName");
                        lastNameList[id] = rs.getString("lastName");
                        id++;
                    }

                    System.out.println();
                    String loginUsername = "";
                    boolean boolUsername = false;

                    // Loops if username doesn't match current database
                    while (!boolUsername) {
                        System.out.print("USERNAME: ");
                        loginUsername = sc.nextLine();
                        boolean boolUsernameValidator = false;

                        // Checks each username in the database and if one matches, proceed to password
                        for (int i = 0; i < usernameList.length; i++) {
                            if (loginUsername.equals(usernameList[i])) {
                                firstName = firstNameList[i];
                                lastName = lastNameList[i];
                                accountName = firstName + " " + lastName;
                                boolUsernameValidator = true;
                                break;
                            }
                        }
                        if (boolUsernameValidator) {
                            boolUsername = true;
                        } else {
                            System.out.println("ERROR: Username doesn't match existing credentials!");
                            System.out.println();
                        }
                    }

                    // Loops if password doesn't match current database
                    boolean boolPassword = false;
                    while (!boolPassword) {
                        System.out.print("PASSWORD: ");
                        String loginPassword = sc.nextLine();
                        boolean boolPasswordValidator = false;

                        // Checks each password in the database and if username and password match, proceed
                        for (int i = 0; i < passwordList.length; i++) {
                            if (loginPassword.equals(passwordList[i]) && loginUsername.equals(usernameList[i])) {
                                boolPasswordValidator = true;
                                break;
                            }
                        }
                        if (boolPasswordValidator) {
                            boolPassword = true;
                        } else {
                            System.out.println("ERROR: Password doesn't match existing credentials!");
                            System.out.println();
                        }
                    }


                } catch (SQLException e) {
                    System.out.println("SERVER ERROR!");
                    e.printStackTrace();
                }
            } else {
                System.out.println("ERROR: Invalid Code!");
                System.out.println();
            }
        }


        try {
            System.out.println();
            System.out.print("Logging in");
            for (int i = 0; i < 3; i++) {
                Thread.sleep(500);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println("          Welcome to PriMart");

        try {
            Connection con = DriverManager.getConnection(databaseURL, username, password);
            Statement st = con.createStatement();

            boolean boolBuyOrSell = false;
            while (!boolBuyOrSell) {
                System.out.println();
                System.out.println("        ---------------------");
                System.out.println("        | CODE |   OPTION   |");
                System.out.println("        ---------------------");
                System.out.println("        |  B   |    BUY     |");
                System.out.println("        |  S   |    SELL    |");
                System.out.println("        |  E   |    EXIT    |");
                System.out.println("        ---------------------");
                System.out.print("Would you like to buy or sell? ");
                String buyOrSell = sc3.nextLine().toUpperCase();

                if (buyOrSell.equals("B")) {
                    boolBuyOrSell = true;
                    ResultSet rs = st.executeQuery("select * from products_database");
                    System.out.println();
                    System.out.println("                  WELCOME TO THE BUY PAGE");
                    System.out.println("     Scroll from hundreds of items posted by sellers!");
                    System.out.println();
                    int[] productIdList = new int[100]; int[] productStocksList = new int[100]; double[] productPriceList = new double[100];
                    String[] productNameList = new String[100]; String[] productSellerList = new String[100];
                    int id = 0;

                    // print all current products stored in the database and store into variables
                    while (rs.next()) {
                        System.out.println("SELLER: " + rs.getString("productSeller"));
                        System.out.println("PRODUCT ID: " + rs.getString("productId"));
                        System.out.println("NAME: " + rs.getString("productName"));
                        System.out.println("PRICE: ₱" + rs.getString("productPrice"));
                        System.out.println("CATEGORY: " + rs.getString("productCategory"));
                        System.out.println("STOCKS: " + rs.getString("productStocks"));
                        System.out.println("CONDITION: " + rs.getString("productCondition"));
                        System.out.println("DESCRIPTION: " + rs.getString("productDescription"));
                        System.out.println();
                        productIdList[id] = rs.getInt("productId");
                        productStocksList[id] = rs.getInt("productStocks");
                        productPriceList[id] = rs.getDouble("productPrice");
                        productNameList[id] = rs.getString("productName");
                        productSellerList[id] = rs.getString("productSeller");
                        id++;
                    }
                    boolean boolDesiredProduct = false;
                    while (!boolDesiredProduct) {
                        System.out.print("Enter Desired Product ID: ");
                        try {
                            int productId = sc2.nextInt();
                            int productCurrentStocks = 0; double productPrice = 0;
                            String productName = ""; String productSeller = "";

                            boolean boolIdValidator = false;

                            // Checks each product id in the database and if the desired product id matches, proceed
                            for (int i = 0; i < productIdList.length; i++) {
                                if (productId == productIdList[i]) {
                                    boolIdValidator = true;
                                    productCurrentStocks = productStocksList[i];
                                    productPrice = productPriceList[i];
                                    productName = productNameList[i];
                                    productSeller = productSellerList[i];
                                    break;
                                }
                            }
                            if (productCurrentStocks < 1) {
                                System.out.println("ERROR: Product is currently out of stock!");
                                System.out.println();
                            } else if (boolIdValidator) {
                                boolean boolQuantity = false;
                                int quantity = 0;
                                while (!boolQuantity) {
                                    System.out.print("Enter Quantity: ");
                                    try {
                                        quantity = sc2.nextInt();
                                        if (quantity > productCurrentStocks) {
                                            System.out.println("ERROR: Insufficient Stocks");
                                            System.out.println();
                                        } else if (quantity < 1) {
                                            System.out.println("ERROR: Invalid Quantity");
                                            System.out.println();
                                        } else {
                                            boolQuantity = true;
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("ERROR: Quantity should not contain alphabetical characters!");
                                        System.out.println();
                                        sc2.nextLine();
                                    }
                                }
                                // Subtract the current stocks of the product to the quantity to be bought by the buyer
                                double totalAmount = productPrice * quantity;
                                productCurrentStocks = productCurrentStocks - quantity;
                                String updateStocks = "UPDATE products_database SET productStocks = " + productCurrentStocks + " WHERE productId = " + productId;
                                int val = st.executeUpdate(updateStocks);
                                if (productCurrentStocks == 0) {
                                    String deleteRow = "DELETE FROM products_database WHERE productStocks = 0";
                                    int val2 = st.executeUpdate(deleteRow);
                                }


                                double change = 0; double money = 0;
                                boolean boolMoneyValidator = false;
                                while (!boolMoneyValidator) {
                                    System.out.println("Amount to be Paid: ₱" + totalAmount);
                                    System.out.print("Enter Money: ");
                                    try {
                                        money = sc2.nextDouble();
                                        if (money < totalAmount) {
                                            System.out.println("ERROR: Insufficient Money!");
                                            System.out.println();
                                        } else {
                                            change = money - totalAmount;
                                            boolMoneyValidator = true;
                                            boolDesiredProduct = true;
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("ERROR: Money should not contain alphabetical characters!");
                                        System.out.println();
                                        sc2.nextLine();
                                    }
                                }

                                // Add the products sold and store in the products sold database
                                int orderId = 0;
                                String addProductsSold = "INSERT INTO products_sold_database VALUES (\"" + orderId + "\",\"" +
                                        productName + "\",\"" + productPrice + "\",\"" + quantity + "\",\"" + totalAmount +
                                        "\",\"" + accountName + "\")";
                                int val4 = st.executeUpdate(addProductsSold);
                                try {
                                    System.out.println();
                                    System.out.print("Purchasing item");
                                    for (int i = 0; i < 3; i++) {
                                        Thread.sleep(500);
                                        System.out.print(".");
                                    }
                                } catch (InterruptedException e) {
                                    System.out.println("ERROR");
                                    e.printStackTrace();
                                }

                                System.out.println();
                                System.out.println();
                                System.out.println("-------PRIMART ONLINE INVOICE-------");
                                System.out.println();
                                System.out.println("BUYER'S NAME: " + accountName);
                                System.out.println("ITEM NAME: " + productName);
                                System.out.println("PRICE: ₱" + productPrice);
                                System.out.println("QUANTITY: " + quantity);
                                System.out.println("TOTAL AMOUNT: " + totalAmount);
                                System.out.println("MONEY RECEIVED: " + money);
                                System.out.println("CHANGE: " + change);
                                System.out.println("SELLER'S NAME: " + productSeller);
                                System.out.println();
                                System.out.println("--------THANK YOU FOR BUYING--------");

                                boolBuyOrSell = false;



                            } else {
                                System.out.println("Product ID doesn't match existing products!");
                                System.out.println();
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("ERROR: Product ID should not contain alphabetical characters!");
                            System.out.println();
                            sc2.nextLine();
                        }
                    }

                } else if (buyOrSell.equals("S")) {
                    boolBuyOrSell = true;
                    boolean boolSellProduct = false;
                    while (!boolSellProduct) {
                        String productName, productCondition, productDescription, productCategory;
                        double productPrice;
                        int productId = 0;
                        int productStocks = 0;

                        System.out.println();
                        productName = "";
                        boolean boolProductName = false;
                        while (!boolProductName) {
                            System.out.print("Enter Product Name: ");
                            productName = sc.nextLine();
                            if (productName.length() < 3) {
                                System.out.println("ERROR: Product Name should be at least 3 letters long");
                                System.out.println();
                            } else {
                                boolProductName = true;
                            }
                        }

                        boolean boolProductPrice = false;
                        productPrice = 0;
                        while (!boolProductPrice)
                            try {
                                System.out.print("Enter Product Price: ₱ ");
                                productPrice = sc.nextDouble();
                                boolProductPrice = true;
                            } catch (InputMismatchException e) {
                                System.out.println("ERROR: Price should only contain numerical characters!");
                                System.out.println();
                                sc.nextLine();
                            }

                        boolean boolProductCategory = false;
                        productCategory = "";
                        while (!boolProductCategory) {
                            System.out.print("Enter Product Category: ");
                            productCategory = sc2.nextLine();
                            if (productCategory.matches(".*\\d.*")) {
                                System.out.println("ERROR: Product Category should not contain numerical characters!");
                                System.out.println();
                            } else if (productCategory.length() < 3) {
                                System.out.println("ERROR: Product Category should be at least 3 letters long");
                                System.out.println();
                            } else {
                                boolProductCategory = true;
                            }

                        }

                        System.out.println();
                        System.out.println("----------------------------");
                        System.out.println("| CODE |       OPTION      |");
                        System.out.println("----------------------------");
                        System.out.println("|  N   |         NEW       |");
                        System.out.println("|  G   |    GOOD AS NEW    |");
                        System.out.println("|  U   |        USED       |");
                        System.out.println("----------------------------");

                        boolean boolProductCondition = false;
                        productCondition = "";
                        while (!boolProductCondition) {
                            System.out.print("Enter Product Condition: ");
                            String productConditionInput = sc2.nextLine().toUpperCase();

                            if (productConditionInput.equals("N")) {
                                productCondition = "NEW";
                                boolProductCondition = true;
                            } else if (productConditionInput.equals("G")) {
                                productCondition = "GOOD AS NEW";
                                boolProductCondition = true;
                            } else if (productConditionInput.equals("U")) {
                                productCondition = "USED";
                                boolProductCondition = true;
                            } else {
                                System.out.println("ERROR: Invalid Code!");
                                System.out.println();
                            }
                        }

                        boolean boolProductDescrip = false;
                        productDescription = "";
                        while (!boolProductDescrip) {
                            System.out.print("Enter Product Description: ");
                            productDescription = sc2.nextLine();
                            if (productDescription.length() > 45) {
                                System.out.println("ERROR: Product Description should not exceed 45 characters!");
                            } else if (productDescription.length() < 3) {
                                System.out.println("ERROR: Product Description should be at least 3 letters long");
                            } else {
                                boolProductDescrip = true;
                            }
                        }
                        boolean boolProductStocks = false;
                        while (!boolProductStocks) {
                            System.out.print("Enter Product Stocks: ");
                            try {
                                productStocks = sc2.nextInt();
                                boolProductStocks = true;
                            } catch (InputMismatchException e) {
                                System.out.println("ERROR: Product Stocks should only contain numerical characters!");
                                System.out.println();
                                sc2.nextLine();
                            }
                        }

                        // Store the new product in the products database
                        String addProduct = "INSERT INTO products_database VALUES (\"" + productId + "\",\"" + productName.toUpperCase() + "\",\"" + productPrice + "\"," +
                                "\"" + productCondition.toUpperCase() + "\",\"" + productDescription + "\",\"" + productCategory.toUpperCase() + "\",\"" + productStocks + "\"," +
                                "\"" + accountName + "\")";
                        int val = st.executeUpdate(addProduct);

                        try {
                            System.out.println();
                            System.out.print("Posting Item");
                            for (int i = 0; i < 3; i++) {
                                Thread.sleep(500);
                                System.out.print(".");
                            }
                        } catch (InterruptedException e) {
                            System.out.println("ERROR");
                            e.printStackTrace();
                        }
                        System.out.println();
                        System.out.println("Item Posted!");
                        boolSellProduct = true;

                        boolBuyOrSell = false;

                    }

                } else if (buyOrSell.equals("E")) {
                    System.out.println();
                    System.out.println("Thank you for using PriMart! Add to cart? Shop at PriMart!");
                    boolBuyOrSell = true;
                    System.exit(0);
                } else {
                    System.out.println("ERROR: Invalid Code!");
                }
            }
        } catch (SQLException e) {
            System.out.println("SERVER ERROR");
            e.printStackTrace();
        }
    }
}

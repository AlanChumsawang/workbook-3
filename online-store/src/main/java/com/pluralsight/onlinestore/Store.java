package com.pluralsight.onlinestore;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/***********************************************************
 *  Store Class attributes contains ArrayList of Products and Cart
 ***********************************************************/
public class Store {
    ArrayList<Products> storeInventory = new ArrayList<Products>();
    ArrayList<Products> cart = new ArrayList<Products>();
/***********************************************************
 *  Store Class Constructor initializing storeInventory ArrayList and cart ArrayList
 ***********************************************************/
    public Store(ArrayList<Products> storeInventory) {
        this.storeInventory = storeInventory;
        this.cart = new ArrayList<Products>();
    }

/***********************************************************
 *  Main Method
 ***********************************************************/
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store(new ArrayList<Products>());
        store.getInventory();
        store.displayMenu(scanner);
    }





























/***********************************************************
 *  Method to get Receipt and write to file to receipts folder
 ***********************************************************/
    public void getReceipt(double totalPaid) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter MDY = DateTimeFormatter.ofPattern("hh:mm-MMM-dd-yyyy");
        String formattedDate = today.format(MDY);
        System.out.println("\n\nHere is Your Receipt:\nDate:  " + formattedDate);
        System.out.println("Items Purchased:");
        for (Products product : cart) {
            System.out.println(product.getProductName());
            System.out.println("    " + "SKU: " + product.getSku());
            System.out.printf("    $%.2f\n", product.getPrice());
            System.out.println("    " + "Department: " + product.getDepartment() + "\n");
        }
        double total = calculateTotal(cart);
        System.out.printf("Total: $%.2f\n", total);
        System.out.printf("Total Paid: $%.2f\n", totalPaid);
        System.out.printf("Change: $%.2f\n", (totalPaid - total));
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("receipts/" + formattedDate + ".txt"))) {
            bufferedWriter.write("Items Purchased:\n\n");
            for (Products product : cart) {
                bufferedWriter.write(product.getProductName() + "\n");
                bufferedWriter.write("    " + "SKU: " + product.getSku() + "\n");
                bufferedWriter.write(String.format("    $%.2f\n", product.getPrice()));
                bufferedWriter.write("    " + "Department: " + product.getDepartment() + "\n");
            }
            bufferedWriter.write(String.format("\n\nTotal: $%.2f\n", total));
            bufferedWriter.write(String.format("Total Paid: $%.2f\n", totalPaid));
            bufferedWriter.write(String.format("Change: $%.2f\n", (totalPaid - total)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


/***********************************************************
 *  Method to get Inventory from products.csv file
 ***********************************************************/
    public void getInventory() {
        try {
            FileReader fileReader = new FileReader("src/main/resources/products.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input = bufReader.readLine();
            while ((input = bufReader.readLine()) != null) {
                String[] productInfo = input.split("[|]");
                String sku = productInfo[0];
                String productName = productInfo[1];
                double price = Double.parseDouble(productInfo[2]);
                String department = productInfo[3];
                Products product = new Products(sku, productName, price, department);
                storeInventory.add(product);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

/***********************************************************
 *  Method to check out and pay for items in cart
 ***********************************************************/
    public void checkout() {
        System.out.println("How would you like to pay?");
        System.out.println("1: Cash\n2: Card");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Cash Inserted: ");
                double totalPaid = scanner.nextDouble();
                scanner.nextLine();
                getReceipt(totalPaid);
                break;
            case 2:
                getReceipt(calculateTotal(cart));
                break;
        }
    }

/***********************************************************
 *  Method to display Cart when user chooses to view cart
 ***********************************************************/
    public void displayCart(Scanner scanner) {
        System.out.println("Your cart:\n");
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty");
            System.out.println("See Inventory? (y/n)");
            String userChoice = scanner.nextLine();
            if (userChoice.equalsIgnoreCase("y")) {
                displayInventory(scanner);
            } else {
                displayMenu(scanner);
            }
        } else {
            for (Products product : cart) {
                System.out.println(product.getProductName());
                System.out.println("    " + "SKU: " + product.getSku());
                System.out.printf("    " + "$" + product.getPrice() + "\n");
                System.out.println("    " + "Department: " + product.getDepartment() + "\n");
            }
            System.out.printf("Total: $%.2f\n", calculateTotal(cart));
            System.out.println("\n1: Checkout\n2: Remove product\n3: Go Back");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    checkout();
                    break;
                case 2:
                    System.out.println("Enter the SKU of the product you would like to remove: ");
                    String productSku = scanner.nextLine().trim().toUpperCase();
                    removeProduct(productSku);
                    displayCart(scanner);
                    break;
                case 3:
                    displayMenu(scanner);
                    break;
            }
        }
    }

/***********************************************************
 *  Method to calculate total of items in cart
 ***********************************************************/
    public double calculateTotal(ArrayList<Products> cart) {
        double total = 0;
        for (Products product : cart) {
            total += product.getPrice();
        }
        return total;
    }

/***********************************************************
 *  Method to add product to cart
 ***********************************************************/
    public void addProductToCart(Scanner scanner) {
        boolean continueShopping = true;
        while (continueShopping) {
            System.out.println("Enter the sku of the product you would like to add to your cart: ");
            String productSku = scanner.nextLine();
            boolean productFound = false;
            for (Products items : storeInventory) {
                if (items.getSku().equalsIgnoreCase(productSku)) {
                    productFound = true;
                    cart.add(items);
                    System.out.println("\nProduct added to cart\n");
                    System.out.println("Continue shopping? (y/n)");
                    char continueShoppingChoice = scanner.next().charAt(0);
                    scanner.nextLine();
                    if (continueShoppingChoice == 'n') {
                        continueShopping = false;
                        displayCart(scanner);
                    }
                }
            }
            if (!productFound) {
                System.out.println("\n\nProduct not found\n\n");
            }
        }
    }

/***********************************************************
 *  Method to display entire Inventory
 ***********************************************************/
    public void displayInventory(Scanner scanner) {
        for (Products product : storeInventory) {
            System.out.println(product.getProductName());
            System.out.println("    " + "SKU: " + product.getSku());
            System.out.printf("    " + "$ " + product.getPrice() + "\n");
            System.out.println("    " + "Department: " + product.getDepartment() + "\n");
        }
        System.out.println("1: Add product to cart\n2: Search Inventory\n3: Go Back");
        String userChoice = scanner.nextLine();
        switch (userChoice) {
            case "1":
                addProductToCart(scanner);
                break;
            case "2":
                System.out.println("1: Search by name\n2: Search by department\n3: Search by price");
                int searchType = scanner.nextInt();
                scanner.nextLine();
                searchInventory(searchType, scanner);
                break;
            case "3":
                displayMenu(scanner);
                break;
        }
    }

/***********************************************************
 *  Method to remove product from cart
 ***********************************************************/
    public void removeProduct(String productSku) {
        for (Products product : cart) {
            if (product.getSku().equals(productSku)) {
                cart.remove(product);
                System.out.println("Product removed");
                return;
            }
        }
        System.out.println("\n\nProduct not found. Try Again\n\n");
    }

/***********************************************************
 *  Method is used to get user input for how they would like to search the inventory then calls the appropriate search method
 ***********************************************************/
    public void searchInventory(int Type, Scanner scanner) {
        switch (Type) {
            case 1:
                searchByName(scanner);
                break;
            case 2:
                searchByDepartment(scanner);
                break;
            case 3:
                searchByPrice(scanner);
                break;
        }
    }

/***********************************************************
 *  Method that searches the inventory by name and displays the product information
 ***********************************************************/
    public void searchByName(Scanner scanner) {
        System.out.println("Enter the name of the product you would like to search for: ");
        String search = scanner.nextLine();

        for (Products product : storeInventory) {
            if (product.getProductName().toLowerCase().equals(search.toLowerCase())) {
                System.out.println(product.getProductName());
                System.out.println("    " + "SKU: " + product.getSku());
                System.out.printf("    " + "$ " + product.getPrice() + "\n");
                System.out.println("    " + "Department: " + product.getDepartment() + "\n");

            } else if (product.getProductName().toLowerCase().contains(search.toLowerCase())) {
                System.out.println(product.getProductName());
                System.out.println("    " + "SKU: " + product.getSku());
                System.out.printf("    " + "$ " + product.getPrice() + "\n");
                System.out.println("    " + "Department: " + product.getDepartment() + "\n");
            }
        }
        System.out.println("Would you like to add A product to your cart? (y/n)");
        char userChoice = scanner.next().charAt(0);
        scanner.nextLine();
        if (userChoice == 'y') {
           addProductToCart(scanner);
        } else {
            displayMenu(scanner);
        }
    }

/***********************************************************
 *  Method that searches the inventory by department and displays the product information
 ***********************************************************/
    public void searchByDepartment(Scanner scanner) {
        System.out.println("Enter the department of the product you would like to search for: ");
        String search = scanner.nextLine();

        for (Products product : storeInventory) {
            if (product.getDepartment().toLowerCase().equals(search.toLowerCase())) {
                System.out.println(product.getProductName());
                System.out.println("    " + "SKU: " + product.getSku());
                System.out.printf("    " + "$ " + product.getPrice() + "\n");
                System.out.println("    " + "Department: " + product.getDepartment() + "\n");
                System.out.println("Would you like to add this product to your cart? (y/n)");
                char userChoice = scanner.next().charAt(0);
                scanner.nextLine();
                if (userChoice == 'y') {
                    cart.add(product);
                } else {
                    displayMenu(scanner);
                }
            }
        }
    }

/***********************************************************
 *  Method that searches the inventory by price and returns products that are less than or equal to the price entered
 ***********************************************************/
    public void searchByPrice(Scanner scanner) {
        System.out.println("Enter the maximum price of the product you would like to search for: ");
        String search = scanner.nextLine();

        double price = Double.parseDouble(search);
        for (Products product : storeInventory) {
            if (product.getPrice() <= price) {
                System.out.println(product.getProductName());
                System.out.println("    " + "SKU: " + product.getSku());
                System.out.printf("    " + "$ " + product.getPrice() + "\n");
                System.out.println("    " + "Department: " + product.getDepartment() + "\n");
            }
        }
        System.out.println("Would you like to add a product to your cart? (y/n)");
        String userChoice = scanner.nextLine();
        if (userChoice.equalsIgnoreCase("y")) {
            addProductToCart(scanner);
        }
    }

/***********************************************************
 *  Method to display Menu allowing user to choose to display Inventory, Cart, or Exit
 ***********************************************************/
    public void displayMenu(Scanner scanner) {
        System.out.print("""
                
                                          ████████╗██╗  ██╗███████╗                      \s
                                          ╚══██╔══╝██║  ██║██╔════╝                      \s
                                             ██║   ███████║█████╗                        \s
                                             ██║   ██╔══██║██╔══╝                        \s
                                             ██║   ██║  ██║███████╗                      \s
                                             ╚═╝   ╚═╝  ╚═╝╚══════╝                      \s
                                           ██████╗ ███╗   ██╗██╗     ██╗███╗   ██╗███████╗
                                          ██╔═══██╗████╗  ██║██║     ██║████╗  ██║██╔════╝
                                          ██║   ██║██╔██╗ ██║██║     ██║██╔██╗ ██║█████╗ \s
                                          ██║   ██║██║╚██╗██║██║     ██║██║╚██╗██║██╔══╝ \s
                                          ╚██████╔╝██║ ╚████║███████╗██║██║ ╚████║███████╗
                                           ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚═╝╚═╝  ╚═══╝╚══════╝
                                          ███████╗████████╗ ██████╗ ██████╗ ███████╗     \s
                                          ██╔════╝╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝     \s
                                          ███████╗   ██║   ██║   ██║██████╔╝█████╗       \s
                                          ╚════██║   ██║   ██║   ██║██╔══██╗██╔══╝       \s
                                          ███████║   ██║   ╚██████╔╝██║  ██║███████╗     \s
                                          ╚══════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝     \s     \s
                
                1: Display Inventory
                2: Display Cart
                3: Exit
                
                Choose an option: """);
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    displayInventory(scanner);
                    break;
                case 2:
                    displayCart(scanner);
                    break;
                case 3:
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Invalid choice");
                    displayMenu(scanner);
            }
        } catch (Exception e) {
            System.out.println("Invalid choice");
            scanner.nextLine();
            displayMenu(scanner);
        }

    }

}
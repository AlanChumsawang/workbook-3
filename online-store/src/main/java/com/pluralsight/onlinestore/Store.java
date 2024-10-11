package com.pluralsight.onlinestore;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {
    ArrayList<Products> storeInventory = new ArrayList<Products>();
    ArrayList<Products> cart = new ArrayList<Products>();

    public Store(ArrayList<Products> storeInventory) {
        this.storeInventory = storeInventory;
        this.cart = new ArrayList<Products>();
    }

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

    public void checkout() {
        
        System.out.println("Checking out");
    }

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
            System.out.println("Total: $ " + calculateTotal(cart));
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
                    displayCart(scanner);
                    break;
            }
        }
    }

    public double calculateTotal(ArrayList<Products> cart) {
        double total = 0;
        for (Products product : cart) {
            total += product.getPrice();
        }
        return total;
    }

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
    public void searchByName(Scanner scanner) {
        System.out.println("Enter the name of the product you would like to search for: ");
        String search = scanner.nextLine();

        for (Products product : storeInventory) {
            if (product.getProductName().toLowerCase().equals(search.toLowerCase())) {
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
            else if (product.getProductName().toLowerCase().contains(search.toLowerCase())) {
                System.out.println("No exact match found. Here are some similar products:");
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
            else if (product.getDepartment().toLowerCase().contains(search.toLowerCase())) {
                System.out.println("No exact match found. Here are some similar products:");
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
        }
        catch (Exception e) {
            System.out.println("Invalid choice");
            scanner.nextLine();
            displayMenu(scanner);
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store(new ArrayList<Products>());
        store.getInventory();
        store.displayMenu(scanner);
    }
}
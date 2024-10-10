package com.pluralsight.onlinestore;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Objects;
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

    public void displayInventory(Scanner scanner) {
        for (Products product : storeInventory) {
            System.out.println(product.getProductName());
            System.out.println("    " + "SKU: " + product.getSku());
            System.out.printf("    " + "$ " + product.getPrice() + "\n");
            System.out.println("    " + "Department: " + product.getDepartment() + "\n");
        }
        System.out.println("Would you like to add a product to your cart? (y/n)");
        String userChoice = scanner.nextLine();
        if (userChoice.equalsIgnoreCase("y") ){
            addProductToCart(scanner);
        }
        el
    }

    public void checkout() {
        System.out.println("Checking out");
    }

    public void displayCart(Scanner scanner) {
        System.out.println("Your cart:\n");
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty");
        } else {
            for (Products product : cart) {
                System.out.println(product.getProductName());
                System.out.println("    " + "SKU: " + product.getSku());
                System.out.printf("    " + "$" + product.getPrice() + "\n");
                System.out.println("    " + "Department: " + product.getDepartment() + "\n");
            }
            System.out.println("Total: $ " + calculateTotal(cart));
            System.out.println("\n1: Checkout\n2: Remove product\n3: Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Checking out");
                    break;
                case 2:
                    System.out.println("Enter the SKU of the product you would like to remove: ");
                    String productSku = scanner.nextLine().trim().toUpperCase();
                    removeProduct(productSku);
                    break;
                case 3:
                    System.out.println("Exiting");
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
            for (Products items : storeInventory) {
                if (items.getSku().equalsIgnoreCase(productSku)) {
                    cart.add(items);
                    System.out.println("Product added to cart");
                    System.out.println("Continue shopping? (y/n)");
                    char continueShoppingChoice = scanner.next().charAt(0);
                    scanner.nextLine();
                    if (continueShoppingChoice == 'n') {
                        continueShopping = false;
                    }
                }
            }
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
        System.out.println("Product not found");
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
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store(new ArrayList<Products>());
        store.getInventory();
        store.displayMenu(scanner);
    }
}
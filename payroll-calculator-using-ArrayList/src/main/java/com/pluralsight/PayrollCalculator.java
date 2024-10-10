package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PayrollCalculator {
    private static final ArrayList<Employees> employeesList = new ArrayList<>();

    public static void searchEmployee() {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Enter the employee ID: ");
        int employeeID = inputScanner.nextInt();
        inputScanner.nextLine();
        for (Employees employee : employeesList) {
            if (employee.getEmployeeID() == employeeID) {
                System.out.println("\n");
                System.out.println("Employee ID: " + employee.getEmployeeID());
                System.out.println("Employee Name: " + employee.getName(employee));
                System.out.println("Pay Rate: " + employee.getPayRate(employee));
                System.out.println("Total Pay: " + employee.calculatePay(employee) + "\n");
                break;
            }
        }
    }
    public static void printEmployeeData() {
        for (Employees employee : employeesList) {
            System.out.println("Employee ID: " + employee.getEmployeeID());
            System.out.println("Employee Name: " + employee.getName(employee));
            System.out.println("Pay Rate: " + employee.getPayRate(employee));
            System.out.println("Total Pay: " + employee.calculatePay(employee) + "\n");
        }
    }

    public static void getEmployeeData(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input = bufReader.readLine();

            while ((input = bufReader.readLine()) != null) {
                String[] employeeData = input.split("[|]");
                int employeeID = Integer.parseInt(employeeData[0]);
                String name = employeeData[1];
                float hoursWorked = Float.parseFloat(employeeData[2]);
                double payRate = Double.parseDouble(employeeData[3]);
                employeesList.add(new Employees(employeeID, name, payRate, hoursWorked));
            }
        } catch (Exception e) {
            Scanner inputScanner = new Scanner(System.in);
            System.out.println("\n\n\nFile not found\n\n\nEnter the name of the file employee file to process:");
            fileName = inputScanner.nextLine();
            getEmployeeData("src/main/resources/" + fileName);
        }

    }

    public static void writeEmployeeData(String newFileName) {
        try{
            FileWriter fileWriter = new FileWriter(newFileName);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write("id|name|gross pay\n");
            for (Employees employee : employeesList) {
                bufWriter.write(employee.getEmployeeID() + "|" + employee.getName(employee) + "|" + employee.getHoursWorked(employee) + "|" + employee.getPayRate(employee) + "\n");
            }
            bufWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Welcome to the Payroll Calculator!");
        boolean continueMenu = true;
        while (continueMenu) {
            System.out.print("""
                    
                    1: Search Employee by ID
                    2: Display all Employee Data
                    3: Create a new payroll file
                    4: Exit
                    
                    
                    Choose an option: """);
            int choice = inputScanner.nextInt();
            inputScanner.nextLine();

            String filePath = "src/main/resources/";
            if (choice != 4) {
                System.out.println("\nEnter the name of the file employee file to process:");
                String fileName = inputScanner.nextLine();
                getEmployeeData(filePath + fileName);
            }

            switch (choice) {
                case 1:
                    searchEmployee();
                    break;
                case 2:
                    printEmployeeData();
                    break;
                case 3:
                    System.out.print("\nEnter the name of the payroll file to create: ");
                    String newFileName = inputScanner.nextLine();
                    writeEmployeeData(filePath + newFileName);
                    break;
                case 4:
                    System.out.println("\nExiting");
                    continueMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again. \n\n");
            }
        }
    }
}


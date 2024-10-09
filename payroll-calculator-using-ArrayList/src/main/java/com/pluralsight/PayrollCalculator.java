package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class PayrollCalculator {
    static ArrayList<Employees> employeesList = new ArrayList<>();

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

    public static void getEmployeeData() {
        try {
            FileReader fileReader = new FileReader("src/main/resources/employees.csv");
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
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        getEmployeeData();
        boolean continueMenu = true;
        while (continueMenu) {
            System.out.print("""
                    Welcome to the Payroll Calculator!
                    
                    1: Search Employee by ID
                    2: Exit
                    
                    
                    Choose an option: """);
            int choice = inputScanner.nextInt();
            inputScanner.nextLine();

            switch (choice) {
                case 1:
                    searchEmployee();
                    break;
                case 2:
                    System.out.println("Exiting");
                    continueMenu = false;
                    break;
            }
        }
    }
}


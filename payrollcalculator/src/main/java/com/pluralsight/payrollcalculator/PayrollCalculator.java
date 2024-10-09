package com.pluralsight.payrollcalculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class PayrollCalculator {


    public static void searchEmployee() {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Enter the employee ID: ");
        int employeeID = inputScanner.nextInt();
        inputScanner.nextLine();

        try {
            FileReader fileReader = new FileReader("src/main/resources/employees.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);
            String input = bufReader.readLine();

            while ((input = bufReader.readLine()) != null) {
                String[] employee = input.split("[|]");
                int empID = Integer.parseInt(employee[0]);
                if (empID == employeeID) {
                    String name = employee[1];
                    float hoursWorked = Float.parseFloat(employee[2]);
                    double payRate = Double.parseDouble(employee[3]);

                    System.out.println("\n");
                    System.out.println("Employee ID: " + empID);
                    System.out.println("Employee Name: " + name);
                    System.out.println("Hours Worked: " + hoursWorked);
                    System.out.println("Pay Rate: " + payRate);
                    System.out.println("Total Pay: " + (payRate * hoursWorked) + "\n");
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
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


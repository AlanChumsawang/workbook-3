package com.pluralsight.payrollcalculator;

public class Employees {
    private int employeeID;
    private String name;
    private double payRate;
    private float hoursWorked;

    public Employees(int employeeID, String name, double payRate) {
        this.employeeID = employeeID;
        this.name = name;
        this.payRate = payRate;
    }

    public String getName(Employees employee) {
        return name;
    }

    public double getPayRate(Employees employee) {
        return payRate;
    }

    public double getHoursWorked(Employees employee) {
        return hoursWorked;
    }

    public double calculatePay(Employees employee) {
        return payRate * hoursWorked;
    }


}

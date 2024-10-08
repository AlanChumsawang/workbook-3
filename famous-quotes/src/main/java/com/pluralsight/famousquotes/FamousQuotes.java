package com.pluralsight.famousquotes;

import java.util.Objects;
import java.util.Scanner;

public class FamousQuotes {
    static String[] quotes = {
            "Stay hungry, stay foolish.",
            "Knowledge is power.",
            "Simplicity is the ultimate sophistication.",
            "To be or not to be.",
            "I think, therefore I am.",
            "Actions speak louder than words.",
            "Fortune favors the bold.",
            "Live and let live.",
            "Time is money.",
            "Less is more."
    };

    public static void main(String[] args) {
        Scanner inputscanner = new Scanner(System.in);
        boolean on = true;
        while (on) {
            System.out.print("""
                            \s
                            Welcome to Quote Generator!
                            \s
                    Choose a number 1 - 10: 
                    """);

            String keepGoing = null;
            try {
                int userInput = inputscanner.nextInt();
                inputscanner.nextLine();
                System.out.println("\n\n" + quotes[userInput]);
                System.out.println("\nKeep Going? Y/N");
                keepGoing = inputscanner.nextLine();

            } catch (Exception e) {
                System.out.println("\nInvalid Entry. Enter a number 1-10");
            }
            if (Objects.equals(keepGoing, "N") || Objects.equals(keepGoing, "n")) {
                on = false;


            }
        }
    }
}

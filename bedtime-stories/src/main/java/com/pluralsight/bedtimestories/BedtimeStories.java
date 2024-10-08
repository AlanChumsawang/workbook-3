package com.pluralsight.bedtimestories;

import java.io.*;
import java.util.Scanner;

public class BedtimeStories {

    public static void readFile(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            Scanner fileScanner = new Scanner(fis);
            while (fileScanner.hasNextLine()) {
                String input = fileScanner.nextLine();
                System.out.println(input);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public static void main(String[] args) {
        Scanner inputscanner = new Scanner(System.in);

        System.out.print("""
                        Bedtime Stories
                
                1:Goldilocks
                2:Hansel and Gretel
                3:Mary had a Little Lamb
                
                Choose a Bedtime Story: 
                """);
        boolean invalid = true;
        while (invalid) {
            try {
                int userInput = inputscanner.nextInt();
                inputscanner.nextLine();
                invalid = false;

                switch (userInput){
                    case 1:
                        readFile("src/main/resources/goldilocks.txt");
                        break;
                    case 2:
                        readFile("src/main/resources/hansel_and_gretel.txt");
                        break;
                    case 3:
                        readFile("src/main/resources/mary_had_a_little_lamb.txt");
                    default:
                        System.out.println("Enter a number 1-3");
                        invalid = true;
                }

            }
                catch(Exception e){
                    System.out.println("Enter a number 1-3");
                }
        }
    }
}





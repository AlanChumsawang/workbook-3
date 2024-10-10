package com.pluralsight.dateformatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class FormatDates {

    public static void main(String[] args) {
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Today is: " + today);

        DateTimeFormatter MDY =
                DateTimeFormatter.ofPattern("MMM-dd-yyyy ");
        DateTimeFormatter time =
                DateTimeFormatter.ofPattern("HH:mm");


        String formattedTime = today.format(time);
        String formattedDate = today.format(MDY);
        System.out.println(formattedTime + " on " + formattedDate);


    }

}

package com.pluralsight.searchengine;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class SearchEngineLogger {
    private ArrayList<Record> searchLog = new ArrayList<>();

    public void logSearch(String action, String searchQuery) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = now.format(time);
        String formattedDate = now.format(date);
        searchLog.add(new Record(formattedTime, formattedDate, action, searchQuery));
    }


    public void saveLog() {
        try {
            FileWriter writer = new FileWriter("src/main/resources/search.log");
            for (Record record : searchLog) {
                writer.write(record.getDate() + " | " + record.getTime()  + " | " + record.getAction() + " | " + record.getSearchQuery() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SearchEngineLogger logger = new SearchEngineLogger();
        System.out.println(" Welcome to the search engine!");
        boolean exit = false;
        while (!exit) {
            System.out.println("Please enter your search query below or type 'exit' to quit the program.");
            Scanner scanner = new Scanner(System.in);
            String searchQuery = scanner.nextLine();
            if (Objects.equals(searchQuery, "exit")) {
                logger.saveLog();
                exit = true;
            } else {
                String action = "Search";
                logger.logSearch(action, searchQuery);
            }
        }
    }
}

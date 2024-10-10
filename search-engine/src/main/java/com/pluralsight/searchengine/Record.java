package com.pluralsight.searchengine;

public class Record {
    String time;
    String date;
    String action;
    String searchQuery;

    public Record(String time, String date, String action, String searchQuery) {
        this.time = time;
        this.date = date;
        this.action = action;
        this.searchQuery = searchQuery;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getAction() {
        return action;
    }

    public String getSearchQuery() {
        return searchQuery;
    }
}

package com.codecool.shop.model;

import java.io.Serializable;
import java.util.Date;

public class AdminLog implements Serializable {
    private String date;
    private String logLevel;
    private String message;

    public AdminLog(String date, String logLevel, String message) {
        this.date = date;
        this.logLevel = logLevel;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

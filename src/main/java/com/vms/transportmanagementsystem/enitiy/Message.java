package com.vms.transportmanagementsystem.enitiy;

import java.time.LocalDate;

public class Message {
    private String title;
    private String content;

    public Message() {
    }

    public Message(String title, String content, LocalDate date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private LocalDate date;
}

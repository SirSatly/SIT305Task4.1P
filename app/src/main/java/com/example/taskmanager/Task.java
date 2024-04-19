package com.example.taskmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public Task(String id, String title, String description, String dueDate)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        try {
            this.dueDate = DATE_FORMAT.parse(dueDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Task(String title, String description, Date dueDate) {
        this.id = System.currentTimeMillis()+"";
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    private String id;

    public String getId() {
        return id;
    }

    private String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String description;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Date dueDate;

    public  Date getDueDate() {
        return dueDate;
    }
    public String getDueDateString() {
        return DATE_FORMAT.format(dueDate);
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                '}';
    }
}

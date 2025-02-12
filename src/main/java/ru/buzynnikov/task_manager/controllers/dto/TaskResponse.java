package ru.buzynnikov.task_manager.controllers.dto;

import java.time.LocalDateTime;

public class TaskResponse {
    private String title;

    private String description;
    private LocalDateTime date;
    private Long userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TaskResponse(String title, String description, LocalDateTime date, Long userId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.userId = userId;
    }

    public TaskResponse() {
    }
}

package ru.buzynnikov.task_manager.controllers.dto;


import java.time.LocalDateTime;

public class TaskRequest {
    private String title;
    private String description;
    private LocalDateTime deadline;
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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TaskRequest(String title, String description, LocalDateTime deadline, Long userId) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.userId = userId;
    }

    public TaskRequest() {
    }
}

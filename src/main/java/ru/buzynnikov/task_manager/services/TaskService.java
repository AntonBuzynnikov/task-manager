package ru.buzynnikov.task_manager.services;

import ru.buzynnikov.task_manager.controllers.dto.TaskRequest;
import ru.buzynnikov.task_manager.models.Task;

public interface TaskService {
    Task createTask(TaskRequest taskRequest);

    Task getTaskById(long id);

    void updateTask(TaskRequest taskRequest, long id);

    void deleteTask(long id);
}

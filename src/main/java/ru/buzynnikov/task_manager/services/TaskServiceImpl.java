package ru.buzynnikov.task_manager.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.task_manager.controllers.dto.TaskRequest;
import ru.buzynnikov.task_manager.models.Task;
import ru.buzynnikov.task_manager.repositories.TaskRepository;
import ru.buzynnikov.task_manager.services.mappers.TaskMapper;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(TaskRequest taskRequest) {
        Task task = taskMapper.toEntity(taskRequest);
        return saveTask(task);
    }

    private Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    public void updateTask(TaskRequest taskRequest, long id) {
        Task task = getTaskById(id);
        Task updatedTask = taskMapper.toEntity(taskRequest);
        updatedTask.setId(id);
        taskRepository.save(updatedTask);
    }

    @Override
    public void deleteTask(long id) {
        if (taskRepository.existsById(id)) taskRepository.deleteById(id);
    }


}

package ru.buzynnikov.task_manager.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.buzynnikov.task_manager.controllers.dto.TaskRequest;
import ru.buzynnikov.task_manager.controllers.dto.TaskResponse;
import ru.buzynnikov.task_manager.models.Task;

import java.util.Objects;

@Component
public class TaskMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Task toEntity(TaskRequest taskRequest) {
        return Objects.isNull(taskRequest) ? null : modelMapper.map(taskRequest, Task.class);
    }

    public TaskResponse toResponse(Task task) {
        return Objects.isNull(task) ? null : modelMapper.map(task, TaskResponse.class);
    }
}

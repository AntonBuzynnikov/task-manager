package ru.buzynnikov.task_manager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.task_manager.controllers.dto.TaskRequest;
import ru.buzynnikov.task_manager.controllers.dto.TaskResponse;
import ru.buzynnikov.task_manager.models.Task;
import ru.buzynnikov.task_manager.services.TaskService;
import ru.buzynnikov.task_manager.services.mappers.TaskMapper;

import java.net.URI;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    private final TaskMapper mapper;

    public TaskController(TaskService taskService, TaskMapper mapper) {
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @PostMapping("/create")
    private ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest, UriComponentsBuilder builder) {
        Task savedTask = taskService.createTask(taskRequest);
        URI uri = builder.path("/task/{id}").buildAndExpand(savedTask.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toResponse(savedTask));
    }

    @GetMapping("/{id}")
    private ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(taskService.getTaskById(id)));
    }

    @PatchMapping("/update/{id}")
    private ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
        taskService.updateTask(taskRequest, id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

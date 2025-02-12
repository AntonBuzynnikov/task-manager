package ru.buzynnikov.task_manager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.buzynnikov.task_manager.controllers.dto.TaskRequest;
import ru.buzynnikov.task_manager.models.Task;
import ru.buzynnikov.task_manager.repositories.TaskRepository;
import ru.buzynnikov.task_manager.services.TaskService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskServiceTest {



    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:latest"
    );


    @BeforeAll
    static void beforeAll(){
        postgres.start();
    }
    @AfterAll
    static void afterAll(){
        postgres.stop();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp(){
        taskRepository.deleteAll();
    }


    @Test
    void createTaskTest(){
        TaskRequest request = new TaskRequest();
        request.setTitle("test");
        request.setDescription("test");
        request.setDeadline(LocalDateTime.of(2025,2,18,0,0,0));
        request.setUserId(1L);



        Task result = taskService.createTask(request);

        assertEquals("test", result.getTitle());
    }

    @Test
    void getTaskByIdTest(){
        Task mockTask = new Task();
        mockTask.setTitle("test");
        mockTask.setDescription("test");
        mockTask.setDeadline(LocalDateTime.of(2025,2,18,0,0,0));
        mockTask.setUserId(1L);

        Task savedTask = taskRepository.save(mockTask);


        Task result = taskService.getTaskById(savedTask.getId());

        assertEquals(result.getTitle(), mockTask.getTitle());
    }

    @Test
    void deleteTaskTest(){
        Task mockTask = new Task();
        mockTask.setTitle("test");
        mockTask.setDescription("test");
        mockTask.setDeadline(LocalDateTime.of(2025,2,18,0,0,0));
        mockTask.setUserId(1L);

        Task savedTask = taskRepository.save(mockTask);

        taskService.deleteTask(savedTask.getId());

        assertEquals(0, taskRepository.findAll().size());
    }

    @Test
    void updateTaskTest(){
        Task mockTask = new Task();
        mockTask.setTitle("test");
        mockTask.setDescription("test");
        mockTask.setDeadline(LocalDateTime.of(2025,2,18,0,0,0));
        mockTask.setUserId(1L);

        Task savedTask = taskRepository.save(mockTask);

        TaskRequest request = new TaskRequest();
        request.setTitle("test2");
        request.setDescription("test2");
        request.setDeadline(LocalDateTime.of(2025,2,18,0,0,0));
        request.setUserId(1L);

        taskService.updateTask(request, savedTask.getId());
        assertEquals("test2", taskRepository.findById(savedTask.getId()).orElseThrow().getTitle());
    }

}

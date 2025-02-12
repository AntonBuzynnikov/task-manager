package ru.buzynnikov.task_manager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.buzynnikov.task_manager.controllers.TaskController;
import ru.buzynnikov.task_manager.controllers.dto.TaskRequest;
import ru.buzynnikov.task_manager.controllers.dto.TaskResponse;
import ru.buzynnikov.task_manager.models.Task;
import ru.buzynnikov.task_manager.repositories.TaskRepository;
import ru.buzynnikov.task_manager.services.TaskService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIntegrationTest {

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

    @Autowired
    TaskController taskController;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private TaskService taskService;

    @Test
    void createTaskAndGetResponse201AndURIAndContent(){
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("test");
        taskRequest.setDescription("test");
        taskRequest.setDeadline(LocalDateTime.now().plusDays(7));
        taskRequest.setUserId(1L);

        ResponseEntity<TaskResponse> response = restTemplate.postForEntity("/task/create", taskRequest, TaskResponse.class);
        TaskResponse taskResponse = response.getBody();
        URI uri = response.getHeaders().getLocation();
        HttpStatusCode status = response.getStatusCode();

        assertEquals(HttpStatus.CREATED, status);
        assertEquals(taskRequest.getTitle(), Objects.requireNonNull(taskResponse).getTitle());
        assertNotNull(uri);
    }

    @Test
    void getTaskByIdAndGetResponse200AndContent(){
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("test");
        taskRequest.setDescription("test");
        taskRequest.setDeadline(LocalDateTime.now().plusDays(7));
        taskRequest.setUserId(1L);
        Task task = taskService.createTask(taskRequest);

        ResponseEntity<TaskResponse> response = restTemplate.getForEntity("/task/" + task.getId(), TaskResponse.class);
        TaskResponse taskResponse = response.getBody();
        assertEquals(taskRequest.getTitle(), Objects.requireNonNull(taskResponse).getTitle());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateTaskAndGetResponse204(){
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("test");
        taskRequest.setDescription("test");
        taskRequest.setDeadline(LocalDateTime.now().plusDays(7));
        taskRequest.setUserId(1L);
        Task task = taskService.createTask(taskRequest);
        taskRequest.setTitle("updated");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<TaskRequest> entity = new HttpEntity<>(taskRequest, headers);

        ResponseEntity<Void> response = restTemplate.exchange("/task/update/" + task.getId(), HttpMethod.PATCH, entity, Void.class);
        ResponseEntity<TaskResponse> taskResponse = restTemplate.getForEntity("/task/" + task.getId(), TaskResponse.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(taskRequest.getTitle(), Objects.requireNonNull(taskResponse.getBody()).getTitle());
    }

    @Test
    void deleteTaskAndGetResponse204(){
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setTitle("test");
        taskRequest.setDescription("test");
        taskRequest.setDeadline(LocalDateTime.now().plusDays(7));
        taskRequest.setUserId(1L);
        Task task = taskService.createTask(taskRequest);

        ResponseEntity<Void> response = restTemplate.exchange("/task/delete/" + task.getId(), HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }
}

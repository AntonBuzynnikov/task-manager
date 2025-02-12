package ru.buzynnikov.task_manager;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.buzynnikov.task_manager.controllers.dto.UserLoginRequest;
import ru.buzynnikov.task_manager.controllers.dto.UserRequestRegister;
import ru.buzynnikov.task_manager.controllers.dto.UserResponse;
import ru.buzynnikov.task_manager.models.User;
import ru.buzynnikov.task_manager.repositories.UserRepository;
import ru.buzynnikov.task_manager.services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

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
    void beforeEach(){
        userRepository.deleteAll();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void registerUserAndReturnStatus201AndContent(){
        UserRequestRegister userRequestRegister = new UserRequestRegister();
        userRequestRegister.setName("testUser");
        userRequestRegister.setEmail("test@test.com");
        userRequestRegister.setPassword("test");


        ResponseEntity<UserResponse> response = restTemplate.postForEntity("/users/register", userRequestRegister, UserResponse.class);
        URI uri = response.getHeaders().getLocation();
        HttpStatusCode status = response.getStatusCode();
        UserResponse userResponse = response.getBody();

        assertEquals(status, HttpStatus.CREATED);
        assertEquals(Objects.requireNonNull(userResponse).getName(), userRequestRegister.getName());
        assertNotNull(uri);
    }
    @Test
    void loginUserAndReturnStatus200(){
        User user = new User();
        user.setName("testUser");
        user.setEmail("test@test.com");
        user.setPassword("test");
        userRepository.save(user);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@test.com");
        userLoginRequest.setPassword("test");
        HttpEntity<UserLoginRequest> entity = new HttpEntity<>(userLoginRequest);

        ResponseEntity<Void> response = restTemplate.exchange("/users/login", HttpMethod.GET, entity,Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getUserById(){
        User user = new User();
        user.setName("testUser");
        user.setEmail("test@test.com");
        user.setPassword("test");
        User savedUser = userRepository.save(user);
        ResponseEntity<UserResponse> response = restTemplate.getForEntity("/users/{id}", UserResponse.class, savedUser.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

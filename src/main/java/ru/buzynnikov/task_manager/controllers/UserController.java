package ru.buzynnikov.task_manager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.task_manager.controllers.dto.UserLoginRequest;
import ru.buzynnikov.task_manager.controllers.dto.UserRequestRegister;
import ru.buzynnikov.task_manager.controllers.dto.UserResponse;
import ru.buzynnikov.task_manager.models.User;
import ru.buzynnikov.task_manager.services.UserService;
import ru.buzynnikov.task_manager.services.mappers.UserMapper;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper mapper;

    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequestRegister request, UriComponentsBuilder builder) {
        User user = userService.createUser(request);
        URI uri = builder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.toResponse(user));
    }
    @GetMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody UserLoginRequest request) {
        if(userService.loginUser(request)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(mapper.toResponse(user));
    }
}

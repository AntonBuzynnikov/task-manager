package ru.buzynnikov.task_manager.services;

import ru.buzynnikov.task_manager.controllers.dto.UserLoginRequest;
import ru.buzynnikov.task_manager.controllers.dto.UserRequestRegister;
import ru.buzynnikov.task_manager.models.User;

public interface UserService {

    User createUser(UserRequestRegister request);

    boolean loginUser(UserLoginRequest request);

    User getUser(Long id);
}

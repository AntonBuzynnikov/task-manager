package ru.buzynnikov.task_manager.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.buzynnikov.task_manager.controllers.dto.UserRequestRegister;
import ru.buzynnikov.task_manager.controllers.dto.UserResponse;
import ru.buzynnikov.task_manager.models.User;

import java.util.Objects;

@Component
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    public User toEntity(UserRequestRegister userRequestRegister) {
        return Objects.isNull(userRequestRegister) ? null : modelMapper.map(userRequestRegister, User.class);
    }

    public UserResponse toResponse(User user) {
        return Objects.isNull(user) ? null : modelMapper.map(user, UserResponse.class);
    }
}

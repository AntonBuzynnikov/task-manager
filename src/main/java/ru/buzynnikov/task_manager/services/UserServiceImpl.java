package ru.buzynnikov.task_manager.services;

import org.springframework.stereotype.Service;
import ru.buzynnikov.task_manager.controllers.dto.UserLoginRequest;
import ru.buzynnikov.task_manager.controllers.dto.UserRequestRegister;
import ru.buzynnikov.task_manager.models.User;
import ru.buzynnikov.task_manager.repositories.UserRepository;
import ru.buzynnikov.task_manager.services.mappers.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(UserRequestRegister request) {
        return save(userMapper.toEntity(request));
    }

    @Override
    public boolean loginUser(UserLoginRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            return user.getPassword().equals(request.getPassword());
        }
        return false;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    private User save(User user){
        return userRepository.save(user);
    }
}

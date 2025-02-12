package ru.buzynnikov.task_manager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.task_manager.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

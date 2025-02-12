package ru.buzynnikov.task_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.buzynnikov.task_manager.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}

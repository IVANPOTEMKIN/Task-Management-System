package ru.effective_mobile.task_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effective_mobile.task_management_system.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
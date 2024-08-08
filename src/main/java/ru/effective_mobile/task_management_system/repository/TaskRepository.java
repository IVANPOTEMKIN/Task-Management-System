package ru.effective_mobile.task_management_system.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;
import ru.effective_mobile.task_management_system.model.Task;
import ru.effective_mobile.task_management_system.model.User;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByHeaderContainingIgnoreCase(String header, Pageable pageable);

    List<Task> findAllByStatus(StatusTask status, Pageable pageable);

    List<Task> findAllByPriority(PriorityTask priority, Pageable pageable);

    List<Task> findAllByAuthor(User author, Pageable pageable);

    List<Task> findAllByPerformer(User performer, Pageable pageable);
}
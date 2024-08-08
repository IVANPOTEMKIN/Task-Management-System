package ru.effective_mobile.task_management_system.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effective_mobile.task_management_system.model.Comment;
import ru.effective_mobile.task_management_system.model.Task;
import ru.effective_mobile.task_management_system.model.User;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByTask(Task task, Pageable pageable);

    List<Comment> findAllByAuthor(User author, Pageable pageable);

    List<Comment> findAllByTaskAndAuthor(Task task, User author, Pageable pageable);
}
package ru.effective_mobile.task_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effective_mobile.task_management_system.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
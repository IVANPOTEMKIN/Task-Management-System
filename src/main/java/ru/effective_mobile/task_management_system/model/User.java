package ru.effective_mobile.task_management_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.effective_mobile.task_management_system.enums.Role;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @Enumerated(STRING)
    private Role role;

    @OneToMany(mappedBy = "author",
            cascade = ALL,
            orphanRemoval = true)
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "performer",
            cascade = {DETACH, MERGE, PERSIST, REFRESH})
    private List<Task> completedTasks;

    @OneToMany(mappedBy = "author",
            cascade = ALL,
            orphanRemoval = true)
    private List<Comment> comments;
}
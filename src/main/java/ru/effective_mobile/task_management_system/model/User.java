package ru.effective_mobile.task_management_system.model;

import jakarta.persistence.*;
import lombok.*;
import ru.effective_mobile.task_management_system.enums.Role;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "first_name",
            nullable = false)
    private String firstName;

    @Column(name = "last_name",
            nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "author",
            cascade = ALL,
            orphanRemoval = true)
    private List<Task> createdTasks;

    @OneToMany(mappedBy = "performer")
    private List<Task> completedTasks;

    @OneToMany(mappedBy = "author",
            cascade = ALL,
            orphanRemoval = true)
    private List<Comment> comments;
}
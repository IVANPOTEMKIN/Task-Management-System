package ru.effective_mobile.task_management_system.model;

import jakarta.persistence.*;
import lombok.*;
import ru.effective_mobile.task_management_system.enums.Role;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && role == user.role
                && Objects.equals(createdTasks, user.createdTasks)
                && Objects.equals(completedTasks, user.completedTasks)
                && Objects.equals(comments, user.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                firstName,
                lastName,
                email,
                password,
                role,
                createdTasks,
                completedTasks,
                comments);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", createdTasks=" + createdTasks +
                ", completedTasks=" + completedTasks +
                ", comments=" + comments +
                '}';
    }
}
package ru.effective_mobile.task_management_system.model;

import jakarta.persistence.*;
import lombok.*;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String header;

    @Column(nullable = false)
    private String description;

    @Enumerated(STRING)
    @Column(nullable = false)
    private StatusTask status;

    @Enumerated(STRING)
    @Column(nullable = false)
    private PriorityTask priority;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id",
            nullable = false)
    private User author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "performer_id")
    private User performer;

    @Temporal(TIMESTAMP)
    @Column(name = "created_at",
            nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "task",
            cascade = ALL,
            orphanRemoval = true)
    private List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id)
                && Objects.equals(header, task.header)
                && Objects.equals(description, task.description)
                && status == task.status
                && priority == task.priority
                && Objects.equals(author, task.author)
                && Objects.equals(performer, task.performer)
                && Objects.equals(createdAt, task.createdAt)
                && Objects.equals(comments, task.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                header,
                description,
                status,
                priority,
                author,
                performer,
                createdAt,
                comments);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", author=" + author +
                ", performer=" + performer +
                ", createdAt=" + createdAt +
                ", comments=" + comments +
                '}';
    }
}
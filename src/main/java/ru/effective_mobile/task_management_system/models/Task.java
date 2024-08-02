package ru.effective_mobile.task_management_system.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.effective_mobile.task_management_system.enums.PriorityTask;
import ru.effective_mobile.task_management_system.enums.StatusTask;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String header;

    private String description;

    @Enumerated(STRING)
    private StatusTask status;

    @Enumerated(STRING)
    private PriorityTask priority;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH},
            fetch = LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(cascade = ALL,
            fetch = LAZY)
    @JoinColumn(name = "performer_id")
    private User performer;

    @Temporal(TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "task",
            cascade = ALL,
            orphanRemoval = true)
    private List<Comment> comments;
}
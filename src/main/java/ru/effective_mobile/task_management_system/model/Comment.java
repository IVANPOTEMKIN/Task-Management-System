package ru.effective_mobile.task_management_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH},
            fetch = LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH},
            fetch = LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    private String text;

    @Temporal(TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
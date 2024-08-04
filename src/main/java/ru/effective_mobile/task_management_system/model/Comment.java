package ru.effective_mobile.task_management_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id",
            nullable = false)
    private User author;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "task_id",
            nullable = false)
    private Task task;

    @Temporal(TIMESTAMP)
    @Column(name = "created_at",
            nullable = false)
    private LocalDateTime createdAt;
}
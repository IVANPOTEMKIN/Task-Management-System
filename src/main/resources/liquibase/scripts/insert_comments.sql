-- liquibase formatted sql

-- changeset ivan:1
INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Ивана', 1, 4, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Петра', 2, 4, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Ивана', 1, 4, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Петра', 2, 4, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Виктора', 3, 3, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Петра', 2, 3, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Виктора', 3, 3, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Петра', 2, 2, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Ивана', 1, 2, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Петра', 2, 5, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Виктора', 3, 5, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO comments(text, author_id, task_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Новый комментарий от Петра', 2, 5, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));
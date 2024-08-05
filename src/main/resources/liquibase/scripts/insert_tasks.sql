-- liquibase formatted sql

-- changeset ivan:1
INSERT INTO tasks(header, description, status, priority, author_id, performer_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Задача Ивана №1', 'Выполнить задачу', 'IS_PENDING', 'LOW', 1, null, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO tasks(header, description, status, priority, author_id, performer_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Задача Ивана №2', 'Выполнить задачу', 'IN_PROGRESS', 'HIGH', 1, 2, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO tasks(header, description, status, priority, author_id, performer_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Задача Петра №1', 'Выполнить задачу', 'IN_PROGRESS', 'MEDIUM', 2, 3, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO tasks(header, description, status, priority, author_id, performer_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Задача Петра №2', 'Выполнить задачу', 'COMPLETED', 'MEDIUM', 2, 1, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO tasks(header, description, status, priority, author_id, performer_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Задача Виктора №1', 'Выполнить задачу', 'IN_PROGRESS', 'HIGH', 3, 2, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));

INSERT INTO tasks(header, description, status, priority, author_id, performer_id, created_at)
    OVERRIDING SYSTEM VALUE
VALUES ('Задача Виктора №2', 'Выполнить задачу', 'IS_PENDING', 'LOW', 3, null, DATE_TRUNC('SECOND', CURRENT_TIMESTAMP));
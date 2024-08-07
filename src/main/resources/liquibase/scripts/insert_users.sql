-- liquibase formatted sql

-- changeset ivan:1
INSERT INTO users(first_name, last_name, email, password, role)
    OVERRIDING SYSTEM VALUE
VALUES ('Иван', 'Иванов', 'ivan@gmail.com', '$2a$10$.q.8VTjgeHz.glwbxhX9UuiWbXB1axYhUIwXnG7TnIQCqEieCrq16', 'USER');

INSERT INTO users(first_name, last_name, email, password, role)
    OVERRIDING SYSTEM VALUE
VALUES ('Петр', 'Петров', 'petr@gmail.com', '$2a$10$.q.8VTjgeHz.glwbxhX9UuiWbXB1axYhUIwXnG7TnIQCqEieCrq16', 'USER');

INSERT INTO users(first_name, last_name, email, password, role)
    OVERRIDING SYSTEM VALUE
VALUES ('Виктор', 'Викторов', 'viktor@gmail.com', '$2a$10$.q.8VTjgeHz.glwbxhX9UuiWbXB1axYhUIwXnG7TnIQCqEieCrq16','USER');
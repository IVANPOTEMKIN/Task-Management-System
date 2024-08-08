-- liquibase formatted sql

-- changeset ivan:1
CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(255) NOT NULL CHECK (role IN ('USER', 'ADMIN'))
);

CREATE TABLE IF NOT EXISTS tasks
(
    id           BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    header       VARCHAR(255)            NOT NULL,
    description  VARCHAR(255)            NOT NULL,
    status       VARCHAR(255)            NOT NULL CHECK (status IN ('IS_PENDING', 'IN_PROGRESS', 'COMPLETED')),
    priority     VARCHAR(255)            NOT NULL CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH')),
    author_id    BIGINT REFERENCES users NOT NULL,
    performer_id BIGINT REFERENCES users,
    created_at   TIMESTAMP               NOT NULL
);

CREATE TABLE IF NOT EXISTS comments
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    text       VARCHAR(255)            NOT NULL,
    author_id  BIGINT REFERENCES users NOT NULL,
    task_id    BIGINT REFERENCES tasks NOT NULL,
    created_at TIMESTAMP               NOT NULL
);
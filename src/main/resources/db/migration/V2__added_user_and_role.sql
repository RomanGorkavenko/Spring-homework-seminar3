CREATE TABLE roles
(
    id   BIGSERIAL    NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       BIGSERIAL NOT NULL,
    login    VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    id      BIGSERIAL NOT NULL,
    user_id BIGINT    NOT NULL,
    role_id BIGINT    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT role_fkey FOREIGN KEY (role_id)
        REFERENCES roles (id),
    CONSTRAINT user_fkey FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO roles (name)
VALUES ('ADMIN'),
       ('READER');

INSERT INTO users (login, password)
VALUES ('admin', 'admin'),
       ('john', 'john'),
       ('bob', 'bob'),
       ('michael', 'michael'),
       ('auth', 'auth');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 2);

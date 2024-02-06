CREATE TABLE books
(
    id   BIGSERIAL NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE issues
(
    id          BIGSERIAL NOT NULL,
    book_id     BIGINT,
    reader_id   BIGINT,
    issued_at   TIMESTAMP(6),
    returned_at TIMESTAMP(6),
    primary key (id)
);

CREATE TABLE readers
(
    id   BIGSERIAL NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS issues
    ADD CONSTRAINT fk_book_id_books FOREIGN KEY (book_id) REFERENCES books ON DELETE CASCADE;

ALTER TABLE IF EXISTS issues
    ADD CONSTRAINT fk_reader_id_readers FOREIGN KEY (reader_id) REFERENCES readers ON DELETE CASCADE;

INSERT INTO readers (name)
VALUES ('John'),
       ('Bob'),
       ('Michael');

INSERT INTO books (name)
VALUES ('Head First Java'),
       ('Чистый код'),
       ('Think Java'),
       ('Java. Полное руководство'),
       ('Java. Библиотека профессионала'),
       ('Java 8 in action'),
       ('Core Java for the Impatient'),
       ('Effective Java');

INSERT INTO issues (reader_id, book_id, issued_at, returned_at)
VALUES (1, 1, '2024-01-26', null),
       (1, 6, '2024-01-26', null),
       (2, 2, '2024-01-26', null),
       (2, 8, '2024-01-26', null),
       (3, 5, '2024-01-26', null);

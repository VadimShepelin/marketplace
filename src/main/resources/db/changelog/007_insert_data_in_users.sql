--liquibase formatted sql

--changeset Vadim:1
INSERT INTO Users (id, firstname, lastname, balance)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'Ivan', 'Petrov', 20000.50),
    ('550e8400-e29b-41d4-a716-446655440001', 'Anna', 'Ivanova', 32000.75),
    ('550e8400-e29b-41d4-a716-446655440002', 'Sergey', 'Sidorov', 111500.00),
    ('550e8400-e29b-41d4-a716-446655440003', 'Elena', 'Smirnova', 3433000.20),
    ('550e8400-e29b-41d4-a716-446655440004', 'Dmitry', 'Kozlov', 12500.00);
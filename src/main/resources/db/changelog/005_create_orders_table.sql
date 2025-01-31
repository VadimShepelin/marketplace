--liquibase formatted sql

--changeset Vadim:1

create table Orders(
    id uuid PRIMARY KEY,
    total_cost numeric not null,
    status status,
    user_id uuid not null,
    FOREIGN KEY (user_id) REFERENCES Users(id)
)
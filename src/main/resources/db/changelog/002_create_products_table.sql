--liquibase formatted sql

--changeset Vadim:1
create table products
(
    id              uuid PRIMARY KEY,
    name            text                  not null,
    description     text                  not null,
    price           numeric not null check (price > 99),
    quantity        bigint,
    sku             character varying(32) not null,
    created_at      date,
    quantity_update date,
    category        categories
);
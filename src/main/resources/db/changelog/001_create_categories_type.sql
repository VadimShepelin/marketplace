--liquibase formatted sql

--changeset Vadim:1
create type categories as enum ('FOOD', 'FMCG', 'CLOTH','COSMETICS','AUTOMOTIVE_PRODUCTS','PET_SUPPLIES');
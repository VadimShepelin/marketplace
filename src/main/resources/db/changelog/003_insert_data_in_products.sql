--liquibase formatted sql

--changeset Vadim:1
INSERT INTO products (
    id,
    name,
    description,
    price,
    quantity,
    sku,
    created_at,
    quantity_update,
    category
)
VALUES
    (
        '123e4567-e89b-12d3-a456-426655440000',
        'Apple iPhone 14 Pro',
        'Smartphone with 6 GB RAM and 128 GB storage',
        999.99,
        100,
        'IPHONE14PRO',
        '2023-01-01',
        '2023-01-01 12:00:00',
        'CLOTH'
    ),
    (
        '123e4567-e89b-12d3-a456-426655440001',
        'Samsung Galaxy S23 Ultra',
        'Smartphone with 12 GB RAM and 256 GB storage',
        1299.99,
        50,
        'S23ULTRA',
        '2023-02-01',
        '2023-02-01 13:00:00',
        'FMCG'
    ),
    (
        '123e4567-e89b-12d3-a456-426655440002',
        'HP Envy x360',
        'Laptop with AMD Ryzen 5 processor and 16 GB RAM',
        899.99,
        200,
        'HPENVYX360',
        '2023-03-01',
        '2023-03-01 14:00:00',
        'CLOTH'
    ),
    (
        '123e4567-e89b-12d3-a456-426655440003',
        'Canon EOS R6',
        'Camera with 20 MP sensor and Wi-Fi',
        1799.99,
        30,
        'CANONEOSR6',
        '2023-04-01',
        '2023-04-01 15:00:00',
        'FOOD'
    ),
    (
        '123e4567-e89b-12d3-a456-426655440004',
        'Sony PlayStation 5',
        'Gaming console with SSD and DualSense controller',
        499.99,
        150,
        'PS5',
        '2023-05-01',
        '2023-05-01 16:00:00',
        'FMCG'
    );

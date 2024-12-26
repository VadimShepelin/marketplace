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
    ('123e4567-e89b-12d3-a456-426655440000', 'Apple iPhone 14 Pro', 'Smartphone with 6 GB RAM and 128 GB storage', 999.99, 100, 'IPHONE14PRO', '2023-01-01', '2023-01-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440001', 'Samsung Galaxy S23 Ultra', 'Smartphone with 12 GB RAM and 256 GB storage', 1299.99, 50, 'S23ULTRA', '2023-02-01', '2023-02-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440002', 'HP Envy x360', 'Laptop with AMD Ryzen 5 processor and 16 GB RAM', 899.99, 200, 'HPENVYX360', '2023-03-01', '2023-03-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440003', 'Canon EOS R6', 'Camera with 20 MP sensor and Wi-Fi', 1799.99, 30, 'CANONEOSR6', '2023-04-01', '2023-04-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440004', 'Sony PlayStation 5', 'Gaming console with SSD and DualSense controller', 499.99, 150, 'PS5', '2023-05-01', '2023-05-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440005', 'Dell Inspiron 15', 'Laptop with Intel Core i5 processor and 8 GB RAM', 699.99, 120, 'DELLINSPIRON15', '2023-06-01', '2023-06-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440006', 'Nikon D850', 'Camera with 45.7 MP sensor and Wi-Fi', 2499.99, 20, 'NIKOND850', '2023-07-01', '2023-07-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440007', 'Microsoft Xbox Series X', 'Gaming console with SSD and controller', 599.99, 180, 'XBOXSERIESX', '2023-08-01', '2023-08-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440008', 'Lenovo ThinkPad X1 Carbon', 'Laptop with Intel Core i7 processor and 16 GB RAM', 1299.99, 100, 'LENOTHINKPADX1', '2023-09-01', '2023-09-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440009', 'Fujifilm X-T4', 'Camera with 26.1 MP sensor and Wi-Fi', 1999.99, 40, 'FUJIFILMX-T4', '2023-10-01', '2023-10-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440010', 'Nintendo Switch OLED', 'Gaming console with OLED screen and Joy-Con controllers', 399.99, 200, 'NINTENDOSWITCHOLED', '2023-11-01', '2023-11-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440011', 'Asus ROG Zephyrus', 'Laptop with AMD Ryzen 9 processor and 24 GB RAM', 1499.99, 80, 'ASUSROGZEPHYRUS', '2023-12-01', '2023-12-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440012', 'Olympus OM-D E-M1 Mark III', 'Camera with 20.4 MP sensor and Wi-Fi', 1799.99, 30, 'OLYMPUSOM-DEM1MARKIII', '2024-01-01', '2024-01-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440013', 'PlayStation 5 Pro', 'Gaming console with SSD and DualSense controller', 699.99, 150, 'PS5PRO', '2024-02-01', '2024-02-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440014', 'Acer Predator Helios 300', 'Laptop with Intel Core i7 processor and 16 GB RAM', 1099.99, 120, 'ACERPREDATORHELIOS300', '2024-03-01', '2024-03-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440015', 'Panasonic Lumix GH6', 'Camera with 25.2 MP sensor and Wi-Fi', 2299.99, 25, 'PANASONICLUMIXGH6', '2024-04-01', '2024-04-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440016', 'Xbox Series S', 'Gaming console with SSD and controller', 499.99, 180, 'XBOXSERIESS', '2024-05-01', '2024-05-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440017', 'MSI GS66 Stealth', 'Laptop with Intel Core i9 processor and 32 GB RAM', 1599.99, 100, 'MSIGS66STEALTH', '2024-06-01', '2024-06-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440018', 'Sony Alpha a7R V', 'Camera with 61.4 MP sensor and Wi-Fi', 3499.99, 20, 'SONYALPHAa7RV', '2024-07-01', '2024-07-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440019', 'Nintendo Switch Lite', 'Gaming console with OLED screen and Joy-Con controllers', 299.99, 200, 'NINTENDOSWITCHLITE', '2024-08-01', '2024-08-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440020', 'Razer Blade 15', 'Laptop with Intel Core i9 processor and 64 GB RAM', 1999.99, 80, 'RAZERBLADE15', '2024-09-01', '2024-09-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440021', 'Fujifilm X-H2', 'Camera with 40.2 MP sensor and Wi-Fi', 2499.99, 30, 'FUJIFILMX-H2', '2024-10-01', '2024-10-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440022', 'PlayStation 5 Slim', 'Gaming console with SSD and DualSense controller', 599.99, 150, 'PS5SLIM', '2024-11-01', '2024-11-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440023', 'Asus ZenBook 14', 'Laptop with Intel Core i5 processor and 8 GB RAM', 699.99, 120, 'ASUSZENBOOK14', '2024-12-01', '2024-12-01', 'FMCG'),
    ('123e4567-e89b-12d3-a456-426655440024', 'Canon EOS R8', 'Camera with 24.2 MP sensor and Wi-Fi', 1999.99, 25, 'CANONEOSR8', '2025-01-01', '2025-01-01', 'FMCG');
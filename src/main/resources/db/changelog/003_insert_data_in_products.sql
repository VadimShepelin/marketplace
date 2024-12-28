--liquibase formatted sql

--changeset Vadim:1
INSERT INTO products (id, name, description, price, quantity, sku, created_at, quantity_update, category)
VALUES
    (gen_random_uuid(), 'Organic Apple', 'Fresh organic apples', 149.99, 300, 'FOOD-APP-001', now(), now(), 'FOOD'),
    (gen_random_uuid(), 'Shampoo X', 'Herbal anti-dandruff shampoo', 249.99, 150, 'COS-SHAM-002', now(), now(), 'COSMETICS'),
    (gen_random_uuid(), 'Men’s Jacket', 'Winter collection jacket', 799.99, 100, 'CLO-JAC-003', now(), now(), 'CLOTH'),
    (gen_random_uuid(), 'Pet Food Bowl', 'Stainless steel pet food bowl', 119.99, 200, 'PET-BWL-004', now(), now(), 'PET SUPPLIES'),
    (gen_random_uuid(), 'Car Wax Pro', 'Protective car wax', 329.99, 80, 'AUT-WAX-005', now(), now(), 'AUTOMOTIVE PRODUCTS'),
    (gen_random_uuid(), 'Dog Collar', 'Adjustable dog collar', 892.99, 250, 'PET-COL-006', now(), now(), 'PET SUPPLIES'),
    (gen_random_uuid(), 'Sunscreen Lotion', 'SPF 50 waterproof lotion', 179.99, 120, 'COS-SUN-007', now(), now(), 'COSMETICS'),
    (gen_random_uuid(), 'Instant Noodles', 'Spicy chicken flavored noodles', 129.99, 500, 'FOOD-NDL-008', now(), now(), 'FOOD'),
    (gen_random_uuid(), 'Leather Shoes', 'Formal leather shoes', 999.99, 60, 'CLO-SHO-009', now(), now(), 'CLOTH'),
    (gen_random_uuid(), 'Engine Oil X', 'Premium engine oil', 649.99, 90, 'AUT-OIL-010', now(), now(), 'AUTOMOTIVE PRODUCTS'),
    (gen_random_uuid(), 'Cat Toy Mouse', 'Interactive cat toy', 792.99, 300, 'PET-TOY-011', now(), now(), 'PET SUPPLIES'),
    (gen_random_uuid(), 'Face Wash', 'Charcoal face wash', 159.99, 180, 'COS-WASH-012', now(), now(), 'COSMETICS'),
    (gen_random_uuid(), 'Cotton T-shirt', 'Plain white t-shirt', 299.99, 220, 'CLO-TSH-013', now(), now(), 'CLOTH'),
    (gen_random_uuid(), 'Chocolate Bar', 'Dark chocolate with hazelnuts', 149.99, 400, 'FOOD-CHO-014', now(), now(), 'FOOD'),
    (gen_random_uuid(), 'Conditioner Y', 'Silky hair conditioner', 209.99, 160, 'COS-COND-015', now(), now(), 'COSMETICS'),
    (gen_random_uuid(), 'Car Air Freshener', 'Lavender scented air freshener', 929.99, 140, 'AUT-FRESH-016', now(), now(), 'AUTOMOTIVE PRODUCTS'),
    (gen_random_uuid(), 'Leather Belt', 'Genuine leather belt', 449.99, 110, 'CLO-BEL-017', now(), now(), 'CLOTH'),
    (gen_random_uuid(), 'Pet Shampoo', 'Anti-flea shampoo for pets', 189.99, 130, 'PET-SHAM-018', now(), now(), 'PET SUPPLIES'),
    (gen_random_uuid(), 'Energy Drink', 'Sugar-free energy drink', 199.99, 350, 'FMC-DRINK-019', now(), now(), 'FMCG'),
    (gen_random_uuid(), 'Toothpaste Max', 'Fluoride toothpaste', 139.99, 210, 'FMC-PASTE-020', now(), now(), 'FMCG');
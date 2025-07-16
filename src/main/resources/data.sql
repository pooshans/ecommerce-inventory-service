-- Insert sample categories
INSERT INTO category (id, name, description) VALUES
(1, 'Electronics', 'Devices and gadgets'),
(2, 'Books', 'Fiction and non-fiction books'),
(3, 'Clothing', 'Apparel and accessories');

-- Insert sample products
INSERT INTO product (id, name, description, price, category_id) VALUES
(1, 'Smartphone', 'Latest model smartphone', 699.99, 1),
(2, 'Laptop', 'High-performance laptop', 999.99, 1),
(3, 'Novel', 'Bestselling novel', 19.99, 2),
(4, 'T-shirt', 'Cotton t-shirt', 9.99, 3);
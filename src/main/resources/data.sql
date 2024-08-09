-- Roles
INSERT INTO role (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

-- Permissions
INSERT INTO permission (name) VALUES
('products:read'),
('products:write'),
('product-categories:read'),
('product-categories:write');

-- Insert into users
INSERT INTO ism_user (username, password, enabled) VALUES
('user', '$2a$10$2mKtsuIQ4DjC2qp6n1pHuOO4l2clPfsF0z9VPkZbCyuxUJ31TEane', true),
('admin', '$2a$10$5zGg4yUcG7VdB8aHozqcBe3L9RKbEknzchjEqh861HLHhvIGxG7f.', true);

-- Associate roles with users
INSERT INTO user_role (user_id, role_id) VALUES (1, 1), (2, 2);

-- Associate permissions with roles
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1), (2, 1), (2, 2);

-- Product categories
INSERT INTO product_category (name, description) VALUES ('Books', 'A wide range of fiction and non-fiction books.');
INSERT INTO product_category (name, description) VALUES ('Electronics', 'Latest and greatest electronic devices and gadgets.');
INSERT INTO product_category (name, description) VALUES ('Computers', 'High-performance computers and accessories.');
INSERT INTO product_category (name, description) VALUES ('Home', 'Essential items and furnishings for your home.');
INSERT INTO product_category (name, description) VALUES ('Garden', 'Tools and supplies for gardening and outdoor spaces.');
INSERT INTO product_category (name, description) VALUES ('Health', 'Products to support your health and wellness.');

-- Products for the Books category (category_id = 1)
INSERT INTO product (name, description, price, stock, category_id) VALUES ('The Great Gatsby', 'A novel by F. Scott Fitzgerald', 10.99, 50, 1);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('1984', 'A dystopian novel by George Orwell', 8.99, 60, 1);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('To Kill a Mockingbird', 'A classic novel by Harper Lee', 12.49, 30, 1);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('The Catcher in the Rye', 'A novel by J.D. Salinger', 11.99, 40, 1);

-- Products for the Electronics category (category_id = 2)
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Smartphone', 'Latest model smartphone with 128GB storage', 699.99, 25, 2);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Bluetooth Headphones', 'Noise-cancelling wireless headphones', 149.99, 100, 2);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Smartwatch', 'Fitness and health tracking smartwatch', 199.99, 50, 2);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('4K TV', '55-inch 4K Ultra HD Smart TV', 799.99, 15, 2);

-- Products for the Computers category (category_id = 3)
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Laptop', '15 inch laptop with 16GB RAM and 512GB SSD', 999.99, 10, 3);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Gaming Mouse', 'Ergonomic gaming mouse with customizable buttons', 59.99, 75, 3);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Mechanical Keyboard', 'RGB mechanical keyboard with programmable keys', 89.99, 60, 3);

-- Products for the Home category (category_id = 4)
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Vacuum Cleaner', 'High suction vacuum cleaner with HEPA filter', 120.00, 20, 4);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Desk Lamp', 'Adjustable LED desk lamp with multiple brightness settings', 45.00, 35, 4);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Air Purifier', 'Compact air purifier with HEPA filter', 75.00, 25, 4);

-- Products for the Garden category (category_id = 5)
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Lawn Mower', 'Electric lawn mower with 20-inch cutting deck', 300.00, 10, 5);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Garden Hose', 'Durable 50-foot garden hose with spray nozzle', 35.00, 60, 5);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Patio Furniture Set', '4-piece outdoor patio furniture set', 450.00, 5, 5);

-- Products for the Health category (category_id = 6)
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Vitamins', 'Daily multivitamin supplements with essential nutrients', 25.99, 200, 6);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Fitness Tracker', 'Wearable fitness tracker with heart rate monitor', 79.99, 30, 6);
INSERT INTO product (name, description, price, stock, category_id) VALUES ('Yoga Mat', 'Non-slip yoga mat for exercise and meditation', 29.99, 40, 6);

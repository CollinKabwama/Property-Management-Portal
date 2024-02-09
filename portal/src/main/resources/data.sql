
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Alice', 'Johnson', 'alice@example.com', 'password', 'CUSTOMER', '123-456-7890');
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Bob', 'Smith', 'bob@example.com', 'password', 'OWNER', '987-654-3210');
-- -- Insert Users
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('John', 'Doe', 'john.doe@example.com', 'encrypted_password', 'OWNER', '555-1234');
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Jane', 'Smith', 'jane.smith@example.com', 'encrypted_password', 'CUSTOMER', '555-5678');
--
-- -- Insert Addresses
-- INSERT INTO address (line1, line2, city, postal_code, state, country) VALUES ('123 Main St', 'Apt 1', 'Fairfield', '52557', 'Iowa', 'USA');
-- INSERT INTO address (line1, line2, city, postal_code, state, country) VALUES ('456 Elm St', NULL, 'Fairfield', '52556', 'Iowa', 'USA');
--
-- -- Insert Owners
-- INSERT INTO owner (user_id) VALUES (1);
--
-- -- Insert Properties
-- INSERT INTO property (name, description, price, status, listing_type, property_type, bath_rooms, bed_rooms, owner_id, address_id, image_url, construction_date)
-- VALUES ('Lovely Home', 'A beautiful family house.', 250000, 'AVAILABLE', 'SALE', 'HOUSE', 2, 3, 1, 1, 'image_url.jpg', '2010-01-01');
--
-- -- Insert Customers
-- INSERT INTO customer (user_id) VALUES (2);
--
-- -- Insert Offers
-- INSERT INTO offer (offer_amount, offer_status, offer_type, property_id, customer_id)
-- VALUES (240000, 'PENDING', 'CASH', 1, 1);
-- INSERT INTO offer (offer_amount, offer_status, offer_type, property_id, customer_id)
-- VALUES (260000, 'REJECTED', 'CREDIT', 1, 1);

-- Insert Users
INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES
                                                                                 ('Alice', 'Johnson', 'alice@example.com', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'CUSTOMER', '555-0101'),
                                                                                 ('Bob', 'Smith', 'bob@example.com', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'OWNER', '555-0102'),
                                                                                 ('John', 'Doe', 'john.doe@example.com', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'OWNER', '555-0103'),
                                                                                 ('Jane', 'Smith', 'jane.smith@example.com', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'CUSTOMER', '555-0104'),
                                                                                 ('Charlie', 'Brown', 'charlie.brown@example.com', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'CUSTOMER', '555-0105'),
                                                                                 ('Diana', 'Prince', 'diana.prince@example.com', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'OWNER', '555-0106'),
                                                                                 ('Bruce', 'Wayne', 'bruce.wayne@example.com', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'ADMIN', '555-0107'),
                                                                                 ('Clark', 'Kent', 'clark.kent@example.com', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'OWNER', '555-0108');

-- Insert Addresses
INSERT INTO address (line1, line2, city, postal_code, state, country) VALUES
                                                                          ('123 Main St', 'Apt 1', 'Fairfield', '52557', 'Iowa', 'USA'),
                                                                          ('456 Elm St', NULL, 'Fairfield', '52556', 'Iowa', 'USA'),
                                                                          ('789 Oak St', 'Apt 2', 'Fairfield', '52558', 'Iowa', 'USA'),
                                                                          ('101 Pine St', NULL, 'Fairfield', '52559', 'Iowa', 'USA'),
                                                                          ('202 Maple St', 'Apt 3', 'Fairfield', '52560', 'Iowa', 'USA');

-- Insert Owners
INSERT INTO owner (user_id, is_enabled) VALUES
                                            (2, true),
                                            (3, true),
                                            (6, true),
                                            (8, true);

-- Assuming user_id 7 is Admin, so not included in owners

-- Insert Properties
INSERT INTO property (name, description, price, status, listing_type, property_type, bath_rooms, bed_rooms, owner_id, address_id, image_url, construction_date) VALUES
                                                                                                                                                                    ('Lovely Home', 'A beautiful family house.', 250000, 'AVAILABLE', 'SALE', 'HOUSE', 2, 3, 2, 1, '/src/assets/images/house1.png', '2010-01-01'),
                                                                                                                                                                    ('Charming Villa', 'Luxurious villa with a pool.', 500000, 'AVAILABLE', 'SALE', 'APARTMENT', 3, 4, 1, 2, '/src/assets/images/house3.png', '2015-03-12'),
                                                                                                                                                                    ('Urban Apartment', 'Apartment in the heart of the city.', 300000, 'AVAILABLE', 'SALE', 'APARTMENT', 1, 2, 2, 3,'/src/assets/images/house4.png', '2018-05-21'),
                                                                                                                                                                    ('Cozy Cottage', 'A cozy cottage in the countryside.', 200000, 'AVAILABLE', 'SALE', 'APARTMENT', 2, 2, 1, 4, '/src/assets/images/house5.png', '2012-07-15'),
                                                                                                                                                                    ('Modern Condo', 'Stylish and modern condo.', 350000, 'AVAILABLE', 'SALE', 'CONDO', 2, 3, 2, 5, '/src/assets/images/house6.png', '2020-09-10');

-- Insert Customers
INSERT INTO customer (user_id) VALUES
                                   (1),
                                   (4),
                                   (5);

-- Insert Offers
-- Assuming offers on property 1 and 2 by different customers
INSERT INTO offer (offer_amount, offer_status, offer_type, property_id, customer_id) VALUES
                                                                                         (240000, 'PENDING', 'CASH', 1, 1),
                                                                                         (260000, 'REJECTED', 'CREDIT', 1, 3),
                                                                                         (450000, 'PENDING', 'CASH', 2, 1),
                                                                                         (480000, 'ACCEPTED', 'CASH', 2, 2);


-- New-----

--
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES
--                                                                                  ('Alice', 'Johnson', 'alice@example.com', 'encrypted_password', 'CUSTOMER', '555-0101'),
--                                                                                  ('Bob', 'Smith', 'bob@example.com', 'encrypted_password', 'OWNER', '555-0102'),
--                                                                                  ('John', 'Doe', 'john.doe@example.com', 'encrypted_password', 'OWNER', '555-0103'),
--                                                                                  ('Jane', 'Smith', 'jane.smith@example.com', 'encrypted_password', 'CUSTOMER', '555-0104'),
--                                                                                  ('Charlie', 'Brown', 'charlie.brown@example.com', 'encrypted_password', 'CUSTOMER', '555-0105'),
--                                                                                  ('Diana', 'Prince', 'diana.prince@example.com', 'encrypted_password', 'OWNER', '555-0106'),
--                                                                                  ('Bruce', 'Wayne', 'bruce.wayne@example.com', 'encrypted_password', 'ADMIN', '555-0107'),
--                                                                                  ('Clark', 'Kent', 'clark.kent@example.com', 'encrypted_password', 'OWNER', '555-0108');
--
--
-- INSERT INTO address (line1, line2, city, postal_code, state, country) VALUES
--                                                                           ('123 Main St', 'Apt 1', 'Fairfield', '52557', 'Iowa', 'USA'),
--                                                                           ('456 Elm St', NULL, 'Fairfield', '52556', 'Iowa', 'USA'),
--                                                                           ('789 Oak St', 'Apt 2', 'Fairfield', '52558', 'Iowa', 'USA'),
--                                                                           ('101 Pine St', NULL, 'Fairfield', '52559', 'Iowa', 'USA'),
--                                                                           ('202 Maple St', 'Apt 3', 'Fairfield', '52560', 'Iowa', 'USA');
--
-- -- Insert Owners (Associating with user_ids)
-- INSERT INTO owner (user_id, is_enabled) VALUES
--                                             (2, true),
--                                             (3, true),
--                                             (6, true),
--                                             (8, true);
--
-- INSERT INTO property (name, description, price, status, listing_type, property_type, bath_rooms, bed_rooms, owner_id, address_id, image_url, construction_date) VALUES
--                                                                                                                                                                     ('Lovely Home', 'A beautiful family house.', 250000, 'AVAILABLE', 'SALE', 'HOUSE', 2, 3, 2, 1, 'image_url1.jpg', '2010-01-01'),
--                                                                                                                                                                     ('Charming Villa', 'Luxurious villa with a pool.', 500000, 'AVAILABLE', 'SALE', 'VILLA', 3, 4, 3, 2, 'image_url2.jpg', '2015-03-12'),
--                                                                                                                                                                     ('Urban Apartment', 'Apartment in the heart of the city.', 300000, 'AVAILABLE', 'SALE', 'APARTMENT', 1, 2, 6, 3, 'image_url3.jpg', '2018-05-21'),
--                                                                                                                                                                     ('Cozy Cottage', 'A cozy cottage in the countryside.', 200000, 'AVAILABLE', 'SALE', 'COTTAGE', 2, 2, 8, 4, 'image_url4.jpg', '2012-07-15'),
--                                                                                                                                                                     ('Modern Condo', 'Stylish and modern condo.', 350000, 'AVAILABLE', 'SALE', 'CONDO', 2, 3, 2, 5, 'image_url5.jpg', '2020-09-10');



-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Alice', 'Johnson', 'alice@example.com', 'password', 'CUSTOMER', '$2a$12$T2UV91gldCYo4oXe5bDx7OYgtCqUQBFS4zATelokD5hFecNHyYK26');
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Bob', 'Smith', 'bob@example.com', 'password', 'OWNER', '$2a$12$T2UV91gldCYo4oXe5bDx7OYgtCqUQBFS4zATelokD5hFecNHyYK26');
--
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Carol', 'Taylor', 'carol.taylor@example.com', 'password', 'CUSTOMER', '$2a$12$K2PV91hldCYo4oXe5bDx7OYgtCqUQBFS4zATelokD5hFecNHyYK26');
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Dave', 'Johnson', 'dave.johnson@example.com', 'password', 'OWNER', '$2a$12$L3QW92imlEYo4oXe5bDx7OYgtCqUQBFS4zATelokD5hFecNHyYK26');
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Eve', 'Brown', 'eve.brown@example.com', 'password', 'ADMIN', '$2a$12$M4RX93jnmFYo4oXe5bDx7OYgtCqUQBFS4zATelokD5hFecNHyYK26');
--
-- -- Insert Users
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('John', 'Doe', 'john.doe@example.com', 'encrypted_password', 'OWNER', '$2a$12$T2UV91gldCYo4oXe5bDx7OYgtCqUQBFS4zATelokD5hFecNHyYK26');
-- INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Jane', 'Smith', 'jane.smith@example.com', 'encrypted_password', 'CUSTOMER', '$2a$12$T2UV91gldCYo4oXe5bDx7OYgtCqUQBFS4zATelokD5hFecNHyYK26');

-- Insert Addresses
-- INSERT INTO address (line1, line2, city, postal_code, state, country) VALUES ('123 Main St', 'Apt 1', 'Fairfield', '52557', 'Iowa', 'USA');
-- INSERT INTO address (line1, line2, city, postal_code, state, country) VALUES ('456 Elm St', NULL, 'Fairfield', '52556', 'Iowa', 'USA');
--
-- -- Insert Owners
-- INSERT INTO owner (user_id, is_enabled) VALUES (1, true);

-- -- Insert Properties
-- INSERT INTO property (name, description, price, status, listing_type, property_type, bath_rooms, bed_rooms, owner_id, address_id, image_url, construction_date)
-- VALUES ('Lovely Home', 'A beautiful family house.', 250000, 'AVAILABLE', 'SALE', 'HOUSE', 2, 3, 1, 1, 'image_url.jpg', '2010-01-01');

-- -- Insert Customers
-- INSERT INTO customer (user_id) VALUES (2);
--
-- -- Insert Offers
-- INSERT INTO offer (offer_amount, offer_status, offer_type, property_id, customer_id)
-- VALUES (240000, 'PENDING', 'CASH', 1, 1);
-- INSERT INTO offer (offer_amount, offer_status, offer_type, property_id, customer_id)
-- VALUES (260000, 'REJECTED', 'CREDIT', 1, 1);




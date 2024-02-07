
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

INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Alice', 'Johnson', 'alice@example.com', 'password', 'CUSTOMER', '123-456-7890');
INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Bob', 'Smith', 'bob@example.com', 'password', 'OWNER', '987-654-3210');
-- Insert Users
INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('John', 'Doe', 'john.doe@example.com', 'encrypted_password', 'OWNER', '555-1234');
INSERT INTO users (firstname, lastname, email, password, role, phone_number) VALUES ('Jane', 'Smith', 'jane.smith@example.com', 'encrypted_password', 'CUSTOMER', '555-5678');

-- Insert Addresses
INSERT INTO address (line1, line2, city, postal_code, state, country) VALUES ('123 Main St', 'Apt 1', 'Fairfield', '52557', 'Iowa', 'USA');
INSERT INTO address (line1, line2, city, postal_code, state, country) VALUES ('456 Elm St', NULL, 'Fairfield', '52556', 'Iowa', 'USA');

-- Insert Owners
INSERT INTO owner (user_id, is_enabled) VALUES (1, true);

-- Insert Properties
INSERT INTO property (name, description, price, status, listing_type, property_type, bath_rooms, bed_rooms, owner_id, address_id, image_url, construction_date)
VALUES ('Lovely Home', 'A beautiful family house.', 250000, 'AVAILABLE', 'SALE', 'HOUSE', 2, 3, 1, 1, 'image_url.jpg', '2010-01-01');

-- Insert Customers
INSERT INTO customer (user_id) VALUES (2);

-- Insert Offers
INSERT INTO offer (offer_amount, offer_status, offer_type, property_id, customer_id)
VALUES (240000, 'PENDING', 'CASH', 1, 1);
INSERT INTO offer (offer_amount, offer_status, offer_type, property_id, customer_id)
VALUES (260000, 'REJECTED', 'CREDIT', 1, 1);




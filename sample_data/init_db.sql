DROP TABLE customers CASCADE;
DROP TABLE orders CASCADE;
DROP TABLE shippings CASCADE;
DROP TABLE products CASCADE;

DROP TABLE IF EXISTS products;
CREATE TABLE products
(
    id                    serial  NOT NULL PRIMARY KEY,
    name                  text,
    price                 float,
    product_category_name text,
    supplier_name         text,
    description           text
);

DROP TABLE IF EXISTS suppliers;
CREATE TABLE suppliers
(
    id          serial  NOT NULL PRIMARY KEY,
    name        text,
    description text
);

DROP TABLE IF EXISTS product_categories;
CREATE TABLE product_categories
(
    id          serial  NOT NULL PRIMARY KEY,
    name        text,
    department  text,
    description text
);

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id                       serial NOT NULL PRIMARY KEY,
    name                     text,
    email                    text,
    token_for_authentication text,
    sysadmin                 boolean
);

DROP TABLE IF EXISTS customers;
CREATE TABLE customers
(
    id               serial  NOT NULL PRIMARY KEY,
    user_id          integer NOT NULL,
    name             text,
    email            text UNIQUE,
    phone_number     text,
    billing_country  text,
    billing_city     text,
    billing_zipcode  text,
    billing_address  text,
    payment_method   text,
    card_number      text,
    card_holder      text,
    expiring_date    text,
    code_credit_card text,
    username_paypal  text
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id             serial  NOT NULL PRIMARY KEY,
    customer_id    integer NOT NULL,
    product_id     integer NOT NULL,
    quantity       integer,
    shipping_id    integer NOT NULL,
    ordering_date  timestamp without time zone,
    payment_method text,
    is_payed       boolean DEFAULT False
);

DROP TABLE IF EXISTS shippings;
CREATE TABLE shippings
(
    id               serial  NOT NULL PRIMARY KEY,
    order_id         integer NOT NULL,
    shipping_country text,
    shipping_city    text,
    shipping_zipcode text,
    shipping_address text

);

ALTER TABLE ONLY customers
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_shipping_id FOREIGN KEY (shipping_id) REFERENCES shippings (id);

ALTER TABLE ONLY shippings
    ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_product_category_name FOREIGN KEY (product_category_name) REFERENCES product_categories (name);

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_supplier_name FOREIGN KEY (supplier_name) REFERENCES suppliers (name);

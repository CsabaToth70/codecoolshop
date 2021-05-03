DROP TABLE IF EXISTS registration;
CREATE TABLE registration
(
    id       serial NOT NULL PRIMARY KEY,
    name     text,
    email    text,
    password text
);


DROP TABLE IF EXISTS items;
CREATE TABLE items
(
    id            serial NOT NULL PRIMARY KEY,
    name          text,
    price         float,
    category      text,
    supplier_name text,
    picture       text,
    description   text
);


DROP TABLE IF EXISTS customers;
CREATE TABLE customers
(
    id               serial  NOT NULL PRIMARY KEY,
    user_id          integer NOT NULL,
    name             text,
    email            citext UNIQUE,
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
    username_paypal  text,
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id             serial  NOT NULL PRIMARY KEY,
    customer_id    integer NOT NULL,
    item_id        integer NOT NULL,
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
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES registration(id);

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customers(id);

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES items(id);

ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_shipping_id FOREIGN KEY (shipping_id) REFERENCES shippings(id);

ALTER TABLE ONLY shippings
    ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders(id);


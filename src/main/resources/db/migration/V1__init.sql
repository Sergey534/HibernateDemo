CREATE table customers ( id serial NOT NULL PRIMARY KEY, name  varchar(50) NOT NULL UNIQUE);
CREATE table products (id serial NOT NULL PRIMARY KEY, name varchar(50) NOT NULL UNIQUE, price numeric(10,2));

CREATE table customers_products (customer_id integer, product_id integer,price numeric(10,2),
CONSTRAINT customer_fkey FOREIGN KEY (customer_id) REFERENCES customers(id)  ON DELETE CASCADE ,
CONSTRAINT product_fkey FOREIGN KEY (product_id) REFERENCES products(id)  ON DELETE CASCADE);

CREATE INDEX customer_name_idx ON customers (name);
CREATE INDEX product_name_idx ON products (name);

INSERT INTO customers (name)
VALUES ('James'),('William'),('Jacob'),('Michael');

INSERT INTO products (name,price)
VALUES
('Harry_Potter',9.95),
('Twilight',18.21),
('It',11.99),
('Forrest_Gump',11.95),
('Mary_Poppins',24.99);

INSERT INTO customers_products (customer_id, product_id,price)
VALUES (1,3,11.99),(1,2,18.21),(2,5,24.99),(2,2,18.21),(3,1,9.95),(3,4,11.95),(4,1,9.95),(4,5,24.99);
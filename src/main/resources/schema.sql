CREATE TABLE IF NOT EXISTS orders
(
    id   SERIAL PRIMARY KEY,
    date DATE,
    cost NUMERIC
);

CREATE TABLE IF NOT EXISTS products
(
    id         SERIAL PRIMARY KEY,
    orders     INTEGER references orders (id),
    name       TEXT,
    cost       NUMERIC
 );
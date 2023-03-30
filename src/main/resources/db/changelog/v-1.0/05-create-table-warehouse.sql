CREATE TABLE warehouse
(
    id           SERIAL PRIMARY KEY,
    socks_id     INT,
    quantity     INT,
    CONSTRAINT fk_socks_id FOREIGN KEY (socks_id) REFERENCES socks (id)
);
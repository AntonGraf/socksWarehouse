CREATE TABLE socks
(
    id  SERIAL PRIMARY KEY ,
    color TEXT,
    cotton_part SMALLINT CHECK (cotton_part >= 0 AND cotton_part <= 100 ),
    quantity INT CHECK (quantity >= 0)
);
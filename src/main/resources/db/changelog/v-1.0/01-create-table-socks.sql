CREATE TABLE socks
(
    id  SERIAL PRIMARY KEY ,
    color TEXT,
    cottonPart SMALLINT CHECK (cottonPart >= 0 AND cottonPart <= 100 ),
    quantity INT CHECK (quantity >= 0)
);
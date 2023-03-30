CREATE TABLE actionLog
(
    id           SERIAL PRIMARY KEY,
    socks_id     INT,
    operation_id INT,
    quantity     INT,
    created_at   TIMESTAMP,
    state_id     INT,
    CONSTRAINT fk_socks_id FOREIGN KEY (socks_id) REFERENCES socks (id),
    CONSTRAINT fk_operation_id FOREIGN KEY (operation_id) REFERENCES operation (id),
    CONSTRAINT fk_state_id FOREIGN KEY (state_id) REFERENCES state (id)
);
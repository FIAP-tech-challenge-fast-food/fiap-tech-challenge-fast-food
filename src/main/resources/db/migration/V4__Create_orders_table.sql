-- V4__Create_orders_table

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    order_status INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

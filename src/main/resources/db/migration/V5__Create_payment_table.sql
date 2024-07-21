-- V5__Create_payment_table

CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    external_reference VARCHAR(255) NOT NULL,
    order_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES order(id)
);
CREATE TABLE IF NOT EXISTS stock_level (
    product_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,

    PRIMARY KEY (product_id, location_id),
    
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (location_id) REFERENCES location(id)
);
CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL
);

CREATE TABLE brand (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE model (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    brand_id BIGINT NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES brand (id)
);
CREATE TABLE vehicle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    model_id BIGINT NOT NULL,
    modelYear INT NOT NULL,
    vin VARCHAR(17),
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES brand (id),
    FOREIGN KEY (model_id) REFERENCES model (id)
);

CREATE TABLE contract (
    contract_number BIGINT PRIMARY KEY,
    monthly_rate DECIMAL(10,2),
    customer_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicle (id)
);

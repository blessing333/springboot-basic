CREATE TABLE customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) NOT NULL,
    email         varchar(50) NOT NULL,
    last_login_at datetime(6)          DEFAULT NULL,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT unq_user_email UNIQUE (email)
);

CREATE TABLE vouchers
(
    voucher_id  BINARY(16) PRIMARY KEY,
    rate        BIGINT      NOT NULL,
    type        varchar(20) NOT NULL,
    created_at  datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    customer_id BINARY(16)  NULL,
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
        ON DELETE CASCADE
);

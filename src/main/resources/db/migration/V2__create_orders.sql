CREATE TABLE orders
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    order_no   VARCHAR(64)    NOT NULL,
    total      DECIMAL(10, 2) NOT NULL,
    goods_name VARCHAR(255)   NOT NULL,
    CONSTRAINT orders_pk PRIMARY KEY (id),
    CONSTRAINT orders_order_no_uk UNIQUE (order_no)
);

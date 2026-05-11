CREATE TABLE orders
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    order_no   VARCHAR(64)    NOT NULL,
    total      DECIMAL(10, 2) NOT NULL,
    goods_name VARCHAR(255)   NOT NULL,
    CONSTRAINT orders_pk PRIMARY KEY (id),
    CONSTRAINT orders_order_no_uk UNIQUE (order_no)
);
CREATE TABLE products
(
    id BIGINT AUTO_INCREMENT NOT NULL,

    -- 商品名称
    name VARCHAR(255) NOT NULL,

    -- 商品价格
    price DECIMAL(10,2) NOT NULL,

    -- 商品描述
    description TEXT NULL,

    -- 商品库存
    stock INT NOT NULL DEFAULT 0,

    -- 商品图片
    image_url VARCHAR(500) NULL,

    -- 商品状态
    -- 1: 上架
    -- 0: 下架
    status TINYINT NOT NULL DEFAULT 1,

    -- 创建时间
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- 更新时间
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT products_pk PRIMARY KEY (id)
);

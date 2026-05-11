-- 先删除表（解决重复建表问题，按外键依赖顺序删除）
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

-- 用户表
CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- 分类表
CREATE TABLE categories
(
    id   TINYINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- 商品表（最终完整版）
CREATE TABLE products
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)    NOT NULL COMMENT '商品名称',
    price         DECIMAL(10, 2)  NOT NULL COMMENT '商品价格',
    description   TEXT            NULL COMMENT '商品描述',
    stock         INT             NOT NULL DEFAULT 0 COMMENT '商品库存',
    image_url     VARCHAR(500)    NULL COMMENT '商品图片',
    status        TINYINT         NOT NULL DEFAULT 1 COMMENT '1:上架 0:下架',
    category_id   TINYINT         NULL,
    created_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
);

-- 订单表
CREATE TABLE orders
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    order_no   VARCHAR(64)     NOT NULL,
    total      DECIMAL(10, 2)  NOT NULL,
    goods_name VARCHAR(255)    NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (order_no)
);

-- 用户详情表
CREATE TABLE profiles
(
    id             BIGINT NOT NULL,
    bio            LONGTEXT NULL,
    phone_number   VARCHAR(15) NULL,
    date_of_birth  date NULL,
    loyalty_points INT UNSIGNED DEFAULT 0 NULL,
    PRIMARY KEY (id)
);

-- 地址表
CREATE TABLE addresses
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    street  VARCHAR(255) NOT NULL,
    city    VARCHAR(255) NOT NULL,
    state   VARCHAR(255) NOT NULL,
    zip     VARCHAR(255) NOT NULL,
    user_id BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

-- 收藏表
CREATE TABLE wishlist
(
    product_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    PRIMARY KEY (product_id, user_id)
);

-- ===================== 外键约束 =====================
-- 地址关联用户
ALTER TABLE addresses
    ADD CONSTRAINT fk_addresses_user
        FOREIGN KEY (user_id) REFERENCES users (id);
CREATE INDEX idx_addresses_user ON addresses (user_id);

-- 商品关联分类
ALTER TABLE products
    ADD CONSTRAINT fk_products_category
        FOREIGN KEY (category_id) REFERENCES categories (id);
CREATE INDEX idx_products_category ON products (category_id);

-- 收藏关联商品 + 用户
ALTER TABLE wishlist
    ADD CONSTRAINT fk_wishlist_product
        FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE;
ALTER TABLE wishlist
    ADD CONSTRAINT fk_wishlist_user
        FOREIGN KEY (user_id) REFERENCES users (id);
CREATE INDEX idx_wishlist_user ON wishlist (user_id);

-- 用户详情关联用户
ALTER TABLE profiles
    ADD CONSTRAINT fk_profiles_user
        FOREIGN KEY (id) REFERENCES users (id);
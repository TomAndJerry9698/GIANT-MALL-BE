
CREATE TABLE `member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `phone` VARCHAR(13) NOT NULL,
    `role` ENUM('SELLER', 'BUYER', 'ADMIN') NOT NULL DEFAULT 'BUYER'
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL,
    PRIMARY KEY (`id`)
);

-- TODO
-- 카테고리(category) 테이블 추가
--CREATE TABLE category (
--    id BIGINT NOT NULL AUTO_INCREMENT,
--    name VARCHAR(50) NOT NULL,
--    parent_id BIGINT NULL,   -- 상위 카테고리(재귀 구조) 지원
--    PRIMARY KEY (id),
--    FOREIGN KEY (parent_id) REFERENCES category(id)
--);

CREATE TABLE `product` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `description` TEXT NULL,

    -- 가격 정보
    `price` DECIMAL(10,2) NOT NULL,
--    `discount_price` DECIMAL(10,2) NULL,
--    `discount_start` DATETIME NULL,
--    `discount_end` DATETIME NULL,

    -- 재고 관련
    `stock` INT NOT NULL DEFAULT 0,
--    `stock_warning_level` INT NOT NULL DEFAULT 0,
--    `sku_code VARCHAR(50)` UNIQUE NULL,
--    `barcode` VARCHAR(50) UNIQUE NULL,

    -- 상태·노출
--    status ENUM('AVAILABLE','OUT_OF_STOCK','DISCONTINUED','COMING_SOON')
--           NOT NULL DEFAULT 'AVAILABLE',
--    is_visible  BOOLEAN NOT NULL DEFAULT TRUE,
--    is_new      BOOLEAN NOT NULL DEFAULT FALSE,
--    is_featured BOOLEAN NOT NULL DEFAULT FALSE,
--    display_order INT NOT NULL DEFAULT 0,

    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `cart` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `member_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CHECK (quantity > 0),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
	UNIQUE KEY uniq_cart_member_product (member_id, product_id)
);

CREATE TABLE `order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `member_id` BIGINT NOT NULL,
    `order_status` ENUM('PENDING', 'PAID', 'SHIPPED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    `total_price` DECIMAL(12,2) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
);

CREATE TABLE `order_product` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL,
    `unit_price` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    CHECK (quantity > 0),
    FOREIGN KEY (`order_id`) REFERENCES `order`(`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
);
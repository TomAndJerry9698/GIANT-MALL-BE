CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    `phone` VARCHAR(13) NOT NULL,
    `role` ENUM('SELLER', 'BUYER', 'ADMIN') NOT NULL DEFAULT 'BUYER',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL,
--    `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE,
--    `last_login_at` TIMESTAMP NULL,
    PRIMARY KEY (`id`)
);

--CREATE TABLE `member` (
--    `id` BIGINT NOT NULL AUTO_INCREMENT,
--    `user_id` BIGINT NOT NULL,
--    `membership_level` ENUM('GOLD','SILVER','BRONZE') NOT NULL,
--    `started_at` DATE NOT NULL,
--    `expires_at` DATE NOT NULL,
--    `is_active` BOOLEAN NOT NULL DEFAULT TRUE,
--    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (`id`),
--
--    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
--    UNIQUE KEY `uniq_member_user` (`user_id`)
--);

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
--    `stock` INT NOT NULL DEFAULT 0,
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

    -- 평점 집계
--    review_avg   DECIMAL(2,1) NOT NULL DEFAULT 0.0,
--    review_count INT        NOT NULL DEFAULT 0,

    -- 연관 FK
--    category_id BIGINT NULL,
--    seller_id   BIGINT NOT NULL,

    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NULL,

--    FOREIGN KEY (category_id) REFERENCES category(id),
--    FOREIGN KEY (seller_id)   REFERENCES user(id),
    PRIMARY KEY (`id`).unique
);

--CREATE TABLE `cart` (
--    `id` BIGINT NOT NULL AUTO_INCREMENT,
--    `user_id` BIGINT NOT NULL,
--    `product_id` BIGINT NOT NULL,
--    `quantity` INT NOT NULL,
--    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (`id`),
--    CHECK (quantity > 0),
--    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
--    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
--	UNIQUE KEY uniq_cart_user_product (user_id, product_id)
--);

--CREATE TABLE `order` (
--    `id` BIGINT NOT NULL AUTO_INCREMENT,
--    `user_id` BIGINT NOT NULL,
--    `order_status` ENUM('PENDING', 'PAID', 'SHIPPED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
--    `total_price` DECIMAL(12,2) NOT NULL,
--    `receiver_name` VARCHAR(100) NOT NULL,
--    `receiver_phone` VARCHAR(20) NOT NULL,
--    `address` TEXT NOT NULL,
--    `payment_method` ENUM('CARD', 'BANK_TRANSFER', 'CASH') NOT NULL,
--    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (`id`),
--    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
--);

--CREATE TABLE `order_product` (
--    `id` BIGINT NOT NULL AUTO_INCREMENT,
--    `order_id` BIGINT NOT NULL,
--    `product_id` BIGINT NOT NULL,
--    `quantity` INT NOT NULL,
--    `unit_price` DECIMAL(10,2) NOT NULL,
--    PRIMARY KEY (`id`),
--    CHECK (quantity > 0),
--    FOREIGN KEY (`order_id`) REFERENCES `order`(`id`),
--    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
--);

--CREATE TABLE `review` (
--  `id` BIGINT NOT NULL AUTO_INCREMENT,
--  `user_id` BIGINT NOT NULL,
--  `product_id` BIGINT NOT NULL,
--  `rating` INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
--  `comment` TEXT,
--  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--  PRIMARY KEY (`id`),
--  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
--  FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
--  UNIQUE KEY uniq_review_user_product (user_id, product_id)
--);

--CREATE TABLE `delivery` (
--  `id` BIGINT NOT NULL AUTO_INCREMENT,
--  `order_id` BIGINT NOT NULL,
--  `tracking_number` VARCHAR(100) UNIQUE,
--  `delivery_status` ENUM('READY', 'SHIPPING', 'DELIVERED', 'RETURNED') NOT NULL DEFAULT 'READY',
--  `shipped_at` TIMESTAMP NULL,
--  `delivered_at` TIMESTAMP NULL,
--  PRIMARY KEY (`id`),
--  FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`)
--);

--CREATE TABLE `wishlist_group` (
--    `id` BIGINT NOT NULL AUTO_INCREMENT,
--    `user_id` BIGINT NOT NULL,
--    `name` VARCHAR(100) NOT NULL,
--    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (`id`),
--    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
--);

--CREATE TABLE `wishlist_item` (
--    `id` BIGINT NOT NULL AUTO_INCREMENT,
--    `wishlist_group_id` BIGINT NOT NULL,
--    `product_id` BIGINT NOT NULL,
--    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (`id`),
--
--    UNIQUE KEY `uniq_wishlist_group_product` (`wishlist_group_id`, `product_id`),
--
--    FOREIGN KEY (`wishlist_group_id`) REFERENCES `wishlist_group`(`id`) ON DELETE CASCADE,
--    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`) ON DELETE CASCADE
--);

--CREATE TABLE `like` (
--    `id` BIGINT NOT NULL AUTO_INCREMENT,
--    `user_id` BIGINT NOT NULL,
--    `product_id` BIGINT NOT NULL,
--    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    PRIMARY KEY (`id`),
--
--    -- 중복 좋아요 방지 (한 회원이 하나의 상품에 한 번만 좋아요 가능)
--    UNIQUE KEY `uniq_likes_user_product` (`user_id`, `product_id`),
--
--    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
--    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`) ON DELETE CASCADE
--);
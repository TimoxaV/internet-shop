CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internet_shop`.`products` (
    `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 ,
    PRIMARY KEY (`product_id`))

CREATE TABLE `internet_shop`.`users` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `login` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`))

  CREATE TABLE `internet_shop`.`orders` (
  `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`order_id`),
  INDEX `order_user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `order_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)

CREATE TABLE `internet_shop`.`orders_products` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT(11) NOT NULL,
  `product_id` BIGINT(11) NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `order_fk_idx` (`order_id` ASC) VISIBLE,
  INDEX `product_fk_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `order_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `internet_shop`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `internet_shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)

CREATE TABLE `internet_shop`.`shopping_carts` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `user_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)

CREATE TABLE `internet_shop`.`shopping_carts_products` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `cart_id` BIGINT(11) NOT NULL,
  `product_id` BIGINT(11) NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `cart_fk_idx` (`cart_id` ASC) VISIBLE,
  INDEX `products_fk_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `cart_fk`
    FOREIGN KEY (`cart_id`)
    REFERENCES `internet_shop`.`shopping_carts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `products_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `internet_shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`roles` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`))

INSERT INTO roles (name) VALUES ('USER')
INSERT INTO roles (name) VALUES ('ADMIN')

CREATE TABLE `internet_shop`.`users_roles` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `role_id` BIGINT(11) NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `users_roles_fk_idx` (`user_id` ASC) VISIBLE,
  INDEX `roles_users_fk_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `users_roles_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `roles_users_fk`
    FOREIGN KEY (`role_id`)
    REFERENCES `internet_shop`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)

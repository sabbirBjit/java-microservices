CREATE TABLE `t_orders`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `order_number` varchar(50) DEFAULT NULL,
    `sku_code` varchar(255),
    `price` decimal(10,2),
    `quantity` int(11),
    PRIMARY KEY (`id`)
);
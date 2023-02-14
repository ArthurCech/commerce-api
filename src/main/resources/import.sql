INSERT INTO tb_category (name, created_at, updated_at) VALUES ('Electronics', NOW(), NOW());
INSERT INTO tb_category (name, created_at, updated_at) VALUES ('Books', NOW(), NOW());
INSERT INTO tb_category (name, created_at, updated_at) VALUES ('Computers', NOW(), NOW());

INSERT INTO tb_product (name, description, img_url, price, created_at, updated_at) VALUES ('The Lord of the Rings', 'Lorem ipsum dolor sit amet, consectetur.', 'shorturl.at/alE28', 90.5, NOW(), NOW());
INSERT INTO tb_product (name, description, img_url, price, created_at, updated_at) VALUES ('Smart TV', 'Nulla eu imperdiet purus. Maecenas ante.', 'shorturl.at/alE28', 2190.0, NOW(), NOW());
INSERT INTO tb_product (name, description, img_url, price, created_at, updated_at) VALUES ('Macbook Pro', 'Nam eleifend maximus tortor, at mollis.', 'shorturl.at/alE28', 1250.0, NOW(), NOW());
INSERT INTO tb_product (name, description, img_url, price, created_at, updated_at) VALUES ('PC Gamer', 'Donec aliquet odio ac rhoncus cursus.', 'shorturl.at/alE28', 1200.0, NOW(), NOW());
INSERT INTO tb_product (name, description, img_url, price, created_at, updated_at) VALUES ('Rails for Dummies', 'Cras fringilla convallis sem vel faucibus.', 'shorturl.at/alE28', 100.99, NOW(), NOW());

INSERT INTO tb_product_category (product_id, category_id) VALUES (1, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (3, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (4, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (5, 2);

INSERT INTO tb_user (name, email, phone, password, role, created_at, updated_at) VALUES ('Maria Brown', 'maria.brown@gmail.com', '988888888', '$2a$10$xmiJkeYHCAkiedabYMT5ruacEbIj.d.s2BZcSaVG47H2NyEUpiXzC', 'USER', NOW(), NOW());
INSERT INTO tb_user (name, email, phone, password, role, created_at, updated_at) VALUES ('Alex Green', 'alex.green@gmail.com', '977777777', '$2a$10$xmiJkeYHCAkiedabYMT5ruacEbIj.d.s2BZcSaVG47H2NyEUpiXzC', 'ADMIN', NOW(), NOW());

INSERT INTO tb_order (moment, order_status, created_at, updated_at, client_id) VALUES ('2019-06-20T19:53:07Z', 2, NOW(), NOW(), 1);
INSERT INTO tb_order (moment, order_status, created_at, updated_at, client_id) VALUES ('2019-07-21T03:42:10Z', 1, NOW(), NOW(), 2);
INSERT INTO tb_order (moment, order_status, created_at, updated_at, client_id) VALUES ('2019-07-22T15:21:22Z', 1, NOW(), NOW(), 1);

INSERT INTO tb_order_item (product_id, order_id, price, quantity, created_at, updated_at) VALUES (1, 1, 90.5, 2, NOW(), NOW());
INSERT INTO tb_order_item (product_id, order_id, price, quantity, created_at, updated_at) VALUES (3, 1, 1250.0, 1, NOW(), NOW());
INSERT INTO tb_order_item (product_id, order_id, price, quantity, created_at, updated_at) VALUES (3, 2, 1250.0, 2, NOW(), NOW());
INSERT INTO tb_order_item (product_id, order_id, price, quantity, created_at, updated_at) VALUES (5, 3, 100.99, 2, NOW(), NOW());

INSERT INTO tb_payment (moment, order_id, created_at, updated_at) VALUES ('2019-06-20T21:53:07Z', 1, NOW(), NOW());
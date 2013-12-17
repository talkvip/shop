CREATE TABLE `SP_GOODS`
(
   `GOODS_ID`      int(10),
   `GOODS_NAME`    varchar(256),
   `GOODS_DESCR`   varchar(2000),
   `GOODS_PRICE`   decimal(10, 2),
   `GOODS_COUNT`   int(10)
);

CREATE TABLE `SP_USER`
(
   `USER_ID`         int(10),
   `USER_NAME`       varchar(256),
   `USER_ROLE`       int(10),
   `USER_PASSWORD`   varchar(256)
);

CREATE TABLE `SP_ROLE`
(
   `ROLE_ID`            int(10),
   `ROLE_NAME`          varchar(256),
   `ROLE_SPRING_NAME`   varchar(256)
);

CREATE TABLE `SP_PURCHASE`
(
   `PURCHASE_ID`      int(10),
   `PURCHASE_USER`    int(10),
   `PURCHASE_GOODS`   int(10),
   `PURCHASE_PRICE`   decimal(10, 2),
   `PURCHASE_DATE`    datetime
);

ALTER TABLE `sp_role`
ADD PRIMARY KEY (`role_id`);

ALTER TABLE `sp_user`
ADD PRIMARY KEY (`user_id`);

ALTER TABLE `sp_user`
ADD CONSTRAINT `fk_user_role`
FOREIGN KEY (`user_role`)
REFERENCES sp_role (`role_id`)
ON UPDATE NO ACTION
ON DELETE CASCADE;

ALTER TABLE `sp_goods`
ADD PRIMARY KEY (`goods_id`);

ALTER TABLE `sp_purchase`
ADD PRIMARY KEY (`purchase_id`);

ALTER TABLE `sp_purchase`
ADD CONSTRAINT `fk_purchase_user`
FOREIGN KEY (`purchase_user`)
REFERENCES sp_user (`user_id`)
ON UPDATE NO ACTION
ON DELETE CASCADE;

ALTER TABLE `sp_purchase`
ADD CONSTRAINT `fk_purchase_goods`
FOREIGN KEY (`purchase_goods`)
REFERENCES sp_goods (`goods_id`)
ON UPDATE NO ACTION
ON DELETE CASCADE;


drop database if exists db_order;
create database db_order;

use db_order;

-- 订单基本信息表
drop table if exists ord_info;
create table ord_info (
    id                  bigint          primary key comment '订单 id',
    pay_channel         tinyint         comment '支付方式 0微信 1支付宝',
    post_user_id        bigint          comment '订单发起者 id',
    status              tinyint         comment '订单状态 0待发货 1待收获 2已收获',
    c_time              datetime        default now() comment '订单创建时间',
    u_time              datetime        default now() comment '订单修改时间'
);

-- 订单商品关系表
drop table if exists ord_product_relation;
create table ord_product_relation (
    order_id            bigint          comment '订单 id',
    product_id          bigint          comment '商品 id'
);

-- 商家信息
drop table if exists prod_store;
create table prod_store (
    id                  bigint          primary key comment '商家 id',
    name                varchar(20)     comment '商家名称',
    avatar              varchar(20)     default 'www.store.com' comment '商家头像',
    c_time              datetime        default now() comment '商家创建时间',
    u_time              datetime        default now() comment '商家修改时间'
);

-- 商品信息
drop table if exists prod_info;
create table prod_info (
    id                  bigint          primary key comment '商品 id',
    store_id            bigint          comment '商店名称',
    title               varchar(20)     comment '商品标题',
    description         varchar(20)     comment '商品简介',
    price               decimal         comment '商品价格',
    count               int             comment '剩余库存量',
    c_time              datetime        default now() comment '商品创建时间',
    u_time              datetime        default now() comment '商品修改时间'
);

-- 用户信息表
drop table if exists user_info;
create table user_info (
    id                  bigint          primary key comment '用户',
    username            varchar(20)     comment '用户名',
    phone               bigint          comment '手机号',
    address             varchar(64)     comment '住址',
    c_time              datetime        default now() comment '商品创建时间',
    u_time              datetime        default now() comment '商品修改时间'
);

-- 商家信息
insert into prod_store (id, name, avatar, c_time, u_time) values
(1641011010011, '赛格电子城', 'www.storeA.com', now(), now()),
(1641011010022, '超世纪电子商城', 'www.storeB.com', now(), now());

-- 商品信息
insert into prod_info (id, store_id, title, description, price, count, c_time, u_time) values
(1641011011001, 1641011010011, 'iPhone 14', '最新款iPhone 14', 999.99, 150, now(), now()),
(1641011011002, 1641011010011, 'Samsung Galaxy S21', '三星旗舰手机', 799.99, 200, now(), now()),
(1641011011003, 1641011010011, 'Huawei P50', '华为旗舰手机', 699.99, 180, now(), now()),
(1641011011004, 1641011010011, 'Xiaomi Mi 11', '小米旗舰手机', 599.99, 250, now(), now()),
(1641011011005, 1641011010011, 'OnePlus 9', '一加旗舰手机', 729.99, 220, now(), now()),
(1641011011006, 1641011010011, 'OPPO Find X3', 'OPPO旗舰手机', 749.99, 170, now(), now()),
(1641011011007, 1641011010011, 'Vivo X60', 'Vivo旗舰手机', 639.99, 140, now(), now()),
(1641011011008, 1641011010022, 'Google Pixel 6', '谷歌旗舰手机', 599.99, 160, now(), now()),
(1641011011009, 1641011010022, 'Sony Xperia 1', '索尼旗舰手机', 899.99, 130, now(), now()),
(1641011011010, 1641011010022, 'Nokia 8.3', '诺基亚手机', 499.99, 110, now(), now()),
(1641011011011, 1641011010022, 'LG Velvet', 'LG智能手机', 649.99, 190, now(), now()),
(1641011011012, 1641011010022, 'Motorola Edge', '摩托罗拉智能手机', 699.99, 210, now(), now()),
(1641011011013, 1641011010022, 'ASUS ROG Phone 5', '华硕电竞手机', 999.99, 120, now(), now()),
(1641011011014, 1641011010022, 'Realme GT', 'Realme旗舰手机', 499.99, 230, now(), now()),
(1641011011015, 1641011010022, 'ZTE Axon 30', '中兴手机', 599.99, 175, now(), now());

-- 用户信息
insert into user_info (id, username, phone, address, c_time, u_time) values
(1641011011001, '陈亦康', 12345678901, '北京市朝阳区', now(), now());


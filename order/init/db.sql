drop database if exists db_order;
create database db_order;

use db_order;

-- 订单基本信息表
drop table if exists ord_info;
create table ord_info (
    order_id            bigint          primary key auto_increment comment '订单 id',
    pay_channel         tinyint         comment '支付方式 0微信 1支付宝',
    post_user_id        bigint          comment '订单发起者 id',
    status              tinyint         comment '订单状态 0待发货 1待收获 2已收获',
    c_time              datetime        default now() comment '订单创建时间',
    u_time              datetime        default now() comment '订单修改时间'
) auto_increment 10000;

-- 订单商品关系表
drop table if exists ord_product_relation;
create table ord_product_relation (
    order_id            bigint          comment '订单 id',
    product_id          bigint          comment '商品 id'
);

-- 商品信息
drop table if exists prod_info;
create table prod_info (
    product_id          bigint          primary key comment '商品 id',
    store_name          bigint          comment '商店名称',
    title               varchar(20)     comment '商品名称',
    description         varchar(20)     comment '商品简介',
    price               decimal         comment '商品价格',
    c_time              datetime        default now() comment '商品创建时间',
    u_time              datetime        default now() comment '商品修改时间'
);

-- 库存信息
drop table if exists prod_stat;
create table prod_stat (
    product_id          bigint          primary key comment '商品 id',
    count               int             comment '剩余库存量',
    u_time              datetime        default now() comment '修改时间'
);

-- 用户信息表
drop table if exists user_info;
create table user_info (
    user_id             bigint          primary key comment '用户',
    username            varchar(20)     comment '用户名',
    phone               bigint          comment '手机号',
    address             varchar(64)     comment '住址'
);

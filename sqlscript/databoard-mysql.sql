DROP TABLE IF EXISTS `db_user`;
CREATE TABLE `db_user`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`       varchar(100) not NULL COMMENT '名称',
    `email`      varchar(50) default null COMMENT '邮箱',
    `password`   varchar(50) not null COMMENT '应用密码',
    `db_create`     datetime     not null COMMENT '创建时间',
    `db_modified`   datetime     not null COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户表';
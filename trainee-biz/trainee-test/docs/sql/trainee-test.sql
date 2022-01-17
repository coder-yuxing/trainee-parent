# 素材表
drop table if exists `t_goods`;
create table `t_goods` (
    `id` bigint unsigned not null auto_increment,
    `name` varchar(128) not null default '' comment '素材名称',
    `code` varchar(128) not null default '' comment '编码',
    `type` tinyint unsigned not null default 1 comment '1 主材 2 辅材 3 软装',
    `category_id` bigint unsigned not null default 0 comment '品类ID',
    `is_enabled` tinyint unsigned not null default 1 comment '启停用：1 启用 0 停用',
    `is_deleted` tinyint unsigned not null default 0 comment '是否已删除：1 已删除 0 未删除',
    `create_user` bigint unsigned not null default 0 comment '创建人ID',
    `create_time` datetime not null default CURRENT_TIMESTAMP comment '创建时间',
    `update_user` bigint unsigned not null default 0 comment '最后编辑人ID',
    `update_time` datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '最后编辑时间',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='素材表';

# 素材属性数值关系表
drop table if exists `t_goods_props`;
create table `t_goods_props` (
    `id` bigint unsigned not null auto_increment,
    `goods_id` bigint unsigned not null default 0 comment '素材ID',
    `prop` varchar(64) not null default '' comment '属性',
    `prop_value` varchar(128) not null default '' comment '属性值',
    `show_type` tinyint unsigned not null default 0 comment '0 单选 1 多选 2 输入',
    primary key (`id`),
    key idx_goods(`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='素材属性数值关系表';


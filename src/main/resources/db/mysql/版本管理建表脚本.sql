CREATE TABLE `metoo_version_client` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '客户端id',
  `unit_id` bigint NOT NULL COMMENT '单位编码',
  `unit_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '客户端名称',
  `area_id` bigint DEFAULT NULL COMMENT '所属区域编码',
  `cur_version_id` bigint DEFAULT NULL COMMENT '当前版本编码',
  `cur_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '当前版本号',
  `app_version_id` bigint DEFAULT NULL COMMENT '指定安装的版本编码',
  `app_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '指定安装的版本',
  `client_status` tinyint DEFAULT NULL COMMENT '客户端状态：0表示离线，1表示在线',
  `delete_status` tinyint DEFAULT '0' COMMENT '删除标记：0表示正常，1表示删除',
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `create_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人名称',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '更新人名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version_status` tinyint DEFAULT NULL COMMENT '版本状态：0表示已完成，1表示未完成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户端版本管理';


CREATE TABLE `metoo_version_client_log` (
                                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                            `unit_id` bigint DEFAULT NULL COMMENT '客户端id对应客户端版本主键',
                                            `version_id` bigint DEFAULT NULL COMMENT '历史版本id',
                                            `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '历史版本号',
                                            `add_time` datetime DEFAULT NULL COMMENT '历史版本时间',
                                            `op_id` bigint DEFAULT NULL COMMENT '历史版本操作人',
                                            `op_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '历史版本操作人名称',
                                            `version_status` tinyint DEFAULT NULL COMMENT '版本状态，1表示已发布，2表示待升级，3表示发布成功，4表示失败，5表示已取消',
                                            `error_info` varchar(500) DEFAULT NULL COMMENT '错误信息',
                                            `delete_status` tinyint DEFAULT '0' COMMENT '删除标记，0表示正常，1表示删除',
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='版本发布记录表';


CREATE TABLE `metoo_area` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '区域id',
                              `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区域名称',
                              `parent_id` bigint DEFAULT NULL COMMENT '父级节点',
                              `unit_id` bigint DEFAULT NULL COMMENT '单位id',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='区域表';
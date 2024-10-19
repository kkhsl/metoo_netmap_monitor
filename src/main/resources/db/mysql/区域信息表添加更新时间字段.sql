ALTER TABLE metoo_area ADD update_time DATETIME DEFAULT now() NULL COMMENT '数据更新日期';


ALTER TABLE metoo_application ADD version_type TINYINT NULL COMMENT '版本类型 0表示增量版本，1表示全量版本';
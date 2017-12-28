CREATE DATABASE /*!32312 IF NOT EXISTS*/`test_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `test_db`;

DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `user_id` VARCHAR(225) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户唯一标识',
  `id_card` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证',
  `user_name` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名称(非空)',
  `mobile_no` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户手机号码',
  `password` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `enabled` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '用户是否可用',
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_password_login_date` TIMESTAMP NULL DEFAULT NULL COMMENT '最后一颁发token时间(登陆时间)',
  `last_password_reset_date` TIMESTAMP NULL DEFAULT NULL COMMENT '最后一次重置密码的时间(重置后,最后一次登陆要失效)',
  `device` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备类型)',
  `device_platform` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备平台',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `mobile_no_unique` (`mobile_no`),
  UNIQUE KEY `id_card_unique` (`id_card`),
  UNIQUE KEY `user_name_unique` (`user_name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户认证所需身份信息';

DROP TABLE IF EXISTS `client_user`;
CREATE TABLE `client_user` (
  `client_id` VARCHAR(225) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端身份id',
  `client_secret` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端身份秘钥',
  `enabled` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '用户是否可用',
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_password_login_date` TIMESTAMP NULL DEFAULT NULL COMMENT '最后一颁发token时间(登陆时间)',
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `mobile_no_unique` (`client_secret`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户端认证所需身份信息';

DROP TABLE IF EXISTS `family_info`;
CREATE TABLE `family_info` (
  `family_id` VARCHAR(225) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '户唯一标识',
  `person_name` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '户主名字',
  `person_card` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '户主名字身份证',
  `family_total` INT DEFAULT 0 COMMENT '户人数',
  `house_status` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL  COMMENT '住房情况',
  `house_area` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '住房面积',
  `poor_status` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '贫困状态',
  `household_where_code` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '户籍地',
  `operator` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作者账号',
  PRIMARY KEY (`family_id`),
  UNIQUE KEY `person_card` (`person_card`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='户信息表';

DROP TABLE IF EXISTS `person_base`;
CREATE TABLE `person_base` (
  `person_id` VARCHAR(225) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '人员唯一标识',
  `family_id` VARCHAR(225) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '户编唯一标识',
  `person_card` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '户主名字身份证',
  `person_name` VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名字',
  `person_name_code` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名字拼音',
  `person_native` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL  COMMENT '民族',
  `person_master` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否户主',
  `master_relation` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '与户主关系',
  `alive_status` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生存状态',
  `link_phone` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `live_where_code` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '居住地',
  `person_job_type` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '职业类型',
  `politics_status` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '政治面貌',
  `health_status` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '健康状态',
  `marriage_status` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '婚姻状态',
  `education_level` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文化程度',
  `graduate_time` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '毕业时间',
  `graduate_school` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '毕业学校',
  `learn_speciality` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学习专业',
  `operator` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作者账号',
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `person_card` (`person_card`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='人基本信息';

DROP TABLE IF EXISTS `person_ext`;
CREATE TABLE `person_ext` (
  `person_ext_id` VARCHAR(225) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '人员扩展唯一标识',
  `person_id` VARCHAR(225) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '人员唯一标识',
  `disable_person` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '残疾状态',
  `disable_person_level` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '残疾等级',

  `participate_type` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL  COMMENT '参保类型',
  `participate_list` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参保险种',
  `no_participate_reason` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '未参保原因',

  `job_type` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '就业状态',
  `no_job_reason` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '未就业原因',
  `retire_time` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '退伍时间',
  `job_start_time` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '就业时间',
  `company_name` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司名称',
  `industry_type` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '行业类型',
  `job_post` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '就业岗位或工种',
  `company_type` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司性质',
  `job_where_type` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '就业地类型',
  `job_where_code` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作地',

  `operator` VARCHAR(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作者账号',
  PRIMARY KEY (`person_ext_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='人扩展信息';

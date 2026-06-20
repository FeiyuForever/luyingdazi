-- ============================================================
-- 野趣搭子 - 数据库初始化脚本
-- 数据库：luyingdazi
-- 字符集：utf8mb4
-- 适用于：MySQL 8.0+
-- ============================================================

CREATE DATABASE IF NOT EXISTS `luyingdazi` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `luyingdazi`;

-- -----------------------------------------------------------
-- 1. 用户表
-- -----------------------------------------------------------
CREATE TABLE `t_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `openid` VARCHAR(128) NOT NULL COMMENT '微信openid',
    `unionid` VARCHAR(128) DEFAULT NULL COMMENT '微信unionid',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `nickname` VARCHAR(50) NOT NULL DEFAULT '露营新人' COMMENT '昵称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像',
    `gender` TINYINT DEFAULT 0 COMMENT '性别：0未知 1男 2女',
    `birthday` DATE DEFAULT NULL COMMENT '生日',
    `city` VARCHAR(50) DEFAULT NULL COMMENT '城市',
    `province` VARCHAR(50) DEFAULT NULL COMMENT '省份',
    `bio` VARCHAR(200) DEFAULT NULL COMMENT '简介',
    `camping_years` TINYINT DEFAULT 0 COMMENT '露营年限：0新手 1一年内 2一到三年 3三年以上',
    `credit_score` INT DEFAULT 100 COMMENT '信用分',
    `member_level` TINYINT DEFAULT 0 COMMENT '会员：0普通 1月度 2季度 3年度',
    `member_expire_time` DATETIME DEFAULT NULL COMMENT '会员到期',
    `invite_code` VARCHAR(10) DEFAULT NULL COMMENT '邀请码',
    `invited_by` BIGINT DEFAULT NULL COMMENT '邀请人ID',
    `coin_balance` BIGINT DEFAULT 0 COMMENT '金币余额',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录',
    `status` TINYINT DEFAULT 1 COMMENT '0封禁 1正常',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_openid` (`openid`),
    KEY `idx_city` (`city`),
    KEY `idx_location` (`latitude`, `longitude`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- -----------------------------------------------------------
-- 2. 用户标签表
-- -----------------------------------------------------------
CREATE TABLE `t_user_tag` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `tag_name` VARCHAR(20) NOT NULL COMMENT '标签名',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_tag_name` (`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户标签表';

-- -----------------------------------------------------------
-- 3. 关注表
-- -----------------------------------------------------------
CREATE TABLE `t_follow` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '关注者',
    `follow_user_id` BIGINT NOT NULL COMMENT '被关注者',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_follow` (`user_id`, `follow_user_id`),
    KEY `idx_follow_user` (`follow_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关注表';

-- -----------------------------------------------------------
-- 4. 动态表
-- -----------------------------------------------------------
CREATE TABLE `t_post` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `content` TEXT COMMENT '内容',
    `images` JSON DEFAULT NULL COMMENT '图片数组',
    `location_name` VARCHAR(100) DEFAULT NULL COMMENT '定位名',
    `longitude` DECIMAL(10,6) DEFAULT NULL,
    `latitude` DECIMAL(10,6) DEFAULT NULL,
    `like_count` INT DEFAULT 0,
    `comment_count` INT DEFAULT 0,
    `is_top` TINYINT DEFAULT 0,
    `top_expire_time` DATETIME DEFAULT NULL,
    `status` TINYINT DEFAULT 1 COMMENT '0删除 1正常 2审核中 3违规',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_created_at` (`created_at` DESC),
    KEY `idx_location` (`latitude`, `longitude`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='动态表';

-- -----------------------------------------------------------
-- 5. 评论表
-- -----------------------------------------------------------
CREATE TABLE `t_comment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `post_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `parent_id` BIGINT DEFAULT 0 COMMENT '父评论ID',
    `reply_user_id` BIGINT DEFAULT NULL COMMENT '回复谁',
    `content` VARCHAR(500) NOT NULL,
    `status` TINYINT DEFAULT 1,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_post_id` (`post_id`, `created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- -----------------------------------------------------------
-- 6. 点赞表
-- -----------------------------------------------------------
CREATE TABLE `t_like` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `target_id` BIGINT NOT NULL,
    `target_type` TINYINT NOT NULL COMMENT '1动态 2评论',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_target` (`user_id`, `target_id`, `target_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';

-- -----------------------------------------------------------
-- 7. 私信会话表
-- -----------------------------------------------------------
CREATE TABLE `t_chat_session` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_a_id` BIGINT NOT NULL COMMENT 'ID较小的用户',
    `user_b_id` BIGINT NOT NULL COMMENT 'ID较大的用户',
    `last_msg` VARCHAR(100) DEFAULT NULL,
    `last_msg_time` DATETIME DEFAULT NULL,
    `a_unread` INT DEFAULT 0,
    `b_unread` INT DEFAULT 0,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_session` (`user_a_id`, `user_b_id`),
    KEY `idx_user_b` (`user_b_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私信会话表';

-- -----------------------------------------------------------
-- 8. 私信消息表
-- -----------------------------------------------------------
CREATE TABLE `t_chat_message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `session_id` BIGINT NOT NULL,
    `sender_id` BIGINT NOT NULL,
    `receiver_id` BIGINT NOT NULL,
    `msg_type` TINYINT DEFAULT 1 COMMENT '1文本 2图片 3位置',
    `content` TEXT,
    `is_read` TINYINT DEFAULT 0,
    `status` TINYINT DEFAULT 1 COMMENT '0撤回 1正常',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_session` (`session_id`, `created_at`),
    KEY `idx_receiver` (`receiver_id`, `is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私信消息表';

-- -----------------------------------------------------------
-- 9. 活动表
-- -----------------------------------------------------------
CREATE TABLE `t_activity` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '发起人',
    `title` VARCHAR(50) NOT NULL,
    `description` TEXT,
    `cover_image` VARCHAR(500) DEFAULT NULL,
    `location_name` VARCHAR(100) NOT NULL,
    `longitude` DECIMAL(10,6) DEFAULT NULL,
    `latitude` DECIMAL(10,6) DEFAULT NULL,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `max_members` INT DEFAULT 0,
    `current_members` INT DEFAULT 1,
    `fee_desc` VARCHAR(100) DEFAULT '免费',
    `requirement` VARCHAR(200) DEFAULT NULL,
    `status` TINYINT DEFAULT 1 COMMENT '0取消 1报名中 2已满 3进行中 4已结束',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_start_time` (`start_time`),
    KEY `idx_status` (`status`),
    KEY `idx_location` (`latitude`, `longitude`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

-- -----------------------------------------------------------
-- 10. 活动报名表
-- -----------------------------------------------------------
CREATE TABLE `t_activity_member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `activity_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `role` TINYINT DEFAULT 2 COMMENT '1发起人 2参与者',
    `status` TINYINT DEFAULT 1 COMMENT '0取消 1已报名',
    `joined_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_activity_user` (`activity_id`, `user_id`),
    KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动报名表';

-- -----------------------------------------------------------
-- 11. 举报表
-- -----------------------------------------------------------
CREATE TABLE `t_report` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `reporter_id` BIGINT NOT NULL,
    `target_id` BIGINT NOT NULL,
    `target_type` TINYINT NOT NULL COMMENT '1用户 2动态 3评论 4活动',
    `reason` TINYINT NOT NULL COMMENT '1色情 2广告 3骚扰 4虚假 5其他',
    `description` VARCHAR(200) DEFAULT NULL,
    `handle_status` TINYINT DEFAULT 0 COMMENT '0待处理 1已处理 2驳回',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_status` (`handle_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='举报表';

-- -----------------------------------------------------------
-- 12. 系统配置表
-- -----------------------------------------------------------
CREATE TABLE `t_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `config_key` VARCHAR(50) NOT NULL,
    `config_value` TEXT NOT NULL,
    `remark` VARCHAR(100) DEFAULT NULL,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

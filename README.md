# 🏕️ 野趣搭子 — 户外露营垂直社交小程序

> 一款专为露营爱好者打造的微信小程序社交平台，帮助用户找到志同道合的露营搭子，组队出行，分享户外生活。

James#22108
Luyingdazi@2026


数据码  2d1b30c675e9b25d7ef8151bd7c5c8a0  （有效截止日期：2026-08-23 16:36:38）

健康检查：https://api.luyingdazi.xyz/actuator/health
登录接口：https://api.luyingdazi.xyz/api/user/wx-login


---

## 一、项目概述

### 1.1 产品定位

**野趣搭子**是一款面向 18-45 岁城市年轻人的户外露营垂直社交微信小程序，核心解决以下痛点：

| 痛点 | 解决方案 |
|------|----------|
| 想露营但找不到同伴 | 基于 LBS 的同城智能匹配 + 组队系统 |
| 不知道去哪里露营 | UGC 图文动态 + 营地推荐 |
| 露营装备不全、经验不足 | 社区交流 + 装备经验分享 |
| 安全顾虑（陌生人社交） | 微信实名体系 + 信用评分 + 举报机制 |
| 社交平台信息太杂 | 垂直领域精准匹配 |

### 1.2 目标用户画像

- **核心用户**：22-35 岁一二线城市白领，周末有户外出行需求
- **次核心用户**：18-25 岁大学生、户外运动爱好者
- **延伸用户**：35-45 岁亲子露营家庭

### 1.3 技术策略

**怎么省钱怎么来**，核心原则：
- 只做微信小程序，不做 App 和 H5
- 能用微信免费能力的全用微信
- 单台 2核2G 服务器部署全部服务
- 先个人主体 0 元上线，验证需求后再升级企业主体开通支付

---

## 二、竞品分析

### 2.1 市场现有产品调研

| 产品名称 | 平台 | 核心功能 | 优势 | 不足 |
|----------|------|----------|------|------|
| **营派** | App | 户外旅游交友、组队露营 | 垂直露营领域先发 | 用户基数小，无小程序 |
| **搭子组局** | App | 兴趣结伴（含露营）、活动组局 | 活动品类丰富 | 非垂直，露营深度不足 |
| **搭da哒** | App | 兴趣搭子活动社区 | 年轻人定位清晰 | 通用社交，缺少户外专业内容 |
| **哼搭** | App | AI 大数据兴趣匹配 | AI 匹配精准 | 泛社交，露营场景覆盖弱 |
| **积目** | App | 年轻人扩圈交友 | 用户量大 | 非垂直，鱼龙混杂 |
| **小红书** | App/小程序 | 露营攻略分享 | 内容生态强 | 无组队功能，无即时通讯 |

### 2.2 竞争差异化

1. **纯小程序**：无需下载，微信内扫码直达，获客成本极低
2. **垂直到露营**：不做泛兴趣社交，只聚焦户外露营
3. **线上+线下闭环**：匹配→组队→出行→分享，完整链路
4. **0 门槛使用**：微信登录即用，不强制实名

---

## 三、功能模块设计

### 3.1 MVP 第一版（4-6 周上线）

> 目标：验证"露营找搭子"需求是否真实存在

| 模块 | 功能 | 优先级 |
|------|------|--------|
| **用户** | 微信一键登录、个人资料编辑（头像/昵称/露营标签）、关注/粉丝 | P0 |
| **动态** | 图文动态发布（最多9图+定位）、评论点赞、收藏 | P0 |
| **匹配** | 同城用户推荐（基于 LBS + 标签匹配） | P0 |
| **私信** | 一对一文字聊天（WebSocket） | P0 |
| **活动** | 发起组队、报名参加、活动列表 | P0 |
| **安全** | 敏感词过滤（本地 DFA）、微信内容安全检测、举报 | P0 |

### 3.2 第二版（验证后迭代，需企业主体）

> 目标：验证用户是否愿意付费

| 模块 | 功能 | 前置条件 |
|------|------|----------|
| **会员** | 月度¥28 / 季度¥68 / 年度¥198 | 企业主体 + 微信支付 |
| **礼物** | 虚拟礼物打赏（帐篷/篝火/星空/房车/极光） | 企业主体 + 微信支付 |
| **置顶** | 动态置顶付费（¥5/24h 同城、¥20/24h 全国） | 企业主体 + 微信支付 |
| **活动抽佣** | 收费活动平台抽 5-10% | 企业主体 + 微信支付 |
| **邀请裂变** | 邀请码机制，邀请成功送会员体验天数 | — |

### 3.3 第三版（有稳定收入后）

| 模块 | 功能 |
|------|------|
| **商户入驻** | 营地方¥2000/年、装备商¥3000/年、向导¥1000/年 |
| **访客记录** | 谁看过我（会员特权） |
| **在线状态** | 查看对方是否在线（会员特权） |
| **超级喜欢** | 优先推荐给对方（会员特权） |

### 3.4 免费 vs 付费权益对比（第二版上线后）

| 功能 | 免费用户 | 付费会员 |
|------|----------|----------|
| 发动态/评论/点赞 | ✅ | ✅ |
| 同城推荐 | 每天5次刷新 | 无限+扩大范围 |
| 私信 | 每天3人 | 无限制 |
| 访客记录 | 最近3人 | 全部 |
| 动态置顶 | ❌ | 付费可用 |
| 在线状态 | ❌ | ✅ |
| 活动优先报名 | ❌ | ✅ |

---

## 四、技术架构

### 4.1 技术选型

| 层级 | 技术方案 | 说明 |
|------|----------|------|
| **前端** | 微信小程序原生 / UniApp | 一端即可 |
| **后端** | Spring Boot 3.x + JDK 17 | 主流 Java 框架 |
| **数据库** | MySQL 8.0（自建在 ECS） | 主业务存储 |
| **缓存** | Redis 7（自建在 ECS） | 会话/热数据/限流 |
| **即时通讯** | WebSocket + Netty | 私信聊天 |
| **文件存储** | 阿里云 OSS（已有 Bucket） | 图片存储 |
| **内容安全** | 微信 msgSecCheck + imgSecCheck | **免费** |
| **登录** | 微信 wx.login + getPhoneNumber | **免费** |
| **消息触达** | 微信订阅消息 | **免费** |
| **定位** | wx.getLocation + 腾讯地图 | **免费** |
| **支付**（第二版） | 微信支付 | 需企业主体 |

### 4.2 部署架构（单机）

```
┌─────────────────────────────────────────────┐
│            微信小程序客户端                    │
└──────────────────┬──────────────────────────┘
                   │ HTTPS
┌──────────────────▼──────────────────────────┐
│     阿里云 ECS 2核2G（8.153.18.148）         │
│     Alibaba Cloud Linux 3 / 上海             │
│                                              │
│  ┌──────────┐  ┌───────┐  ┌──────────┐     │
│  │  Nginx   │  │ MySQL │  │  Redis   │     │
│  │ 反向代理  │  │  8.0  │  │   7.x   │     │
│  │ SSL终止   │  │ 256MB │  │  128MB  │     │
│  └────┬─────┘  └───────┘  └──────────┘     │
│       │                                      │
│  ┌────▼──────────────────────────────┐      │
│  │   Spring Boot 应用                 │      │
│  │   JVM: -Xms256m -Xmx384m         │      │
│  │   含 WebSocket（Netty）           │      │
│  └───────────────────────────────────┘      │
│                                              │
│  Swap 分区 1GB（防止 OOM）                   │
└──────────────────────────────────────────────┘
         │
┌────────▼─────────────────────────────────────┐
│       阿里云 OSS（已有 Bucket）               │
│       图片/视频存储                           │
└──────────────────────────────────────────────┘
```

### 4.3 内存分配规划（2G 总内存）

| 组件 | 分配 | 说明 |
|------|------|------|
| 系统 | ~300MB | Linux 内核+基础进程 |
| MySQL | 256MB | innodb_buffer_pool_size=256M |
| Redis | 128MB | maxmemory 128mb |
| Spring Boot | 384MB | -Xms256m -Xmx384m |
| Nginx | 10MB | 极轻量 |
| **合计** | ~1.1GB | 剩余 900MB 给系统和 Swap |

### 4.4 项目目录结构

```
luyingdazi/
├── README.md                          # 项目说明（本文件）
├── doc/
│   ├── sql/
│   │   ├── init_schema.sql            # 建库建表 DDL
│   │   └── init_data.sql              # 初始化数据
│   └── deploy/
│       ├── docker-compose.yml         # Docker 编排
│       ├── nginx.conf                 # Nginx 配置
│       ├── mysql.cnf                  # MySQL 低内存配置
│       └── deploy-guide.md            # 部署指南
│
├── luyingdazi-server/                 # 后端 Spring Boot
│   ├── pom.xml
│   ├── src/main/java/com/luyingdazi/
│   │   ├── LuyingdaziApplication.java
│   │   ├── common/                    # 公共（常量/枚举/异常/工具/统一响应）
│   │   ├── config/                    # 配置（WebSocket/Redis/OSS/拦截器）
│   │   ├── controller/                # 接口层
│   │   │   ├── UserController.java
│   │   │   ├── PostController.java
│   │   │   ├── ChatController.java
│   │   │   ├── ActivityController.java
│   │   │   └── MatchController.java
│   │   ├── service/                   # 业务层
│   │   │   ├── UserService.java
│   │   │   ├── PostService.java
│   │   │   ├── ChatService.java
│   │   │   ├── ActivityService.java
│   │   │   ├── MatchService.java
│   │   │   └── ContentSecurityService.java
│   │   ├── mapper/                    # 数据访问层
│   │   ├── model/                     # 实体/DTO/VO
│   │   ├── websocket/                 # WebSocket 处理器
│   │   └── filter/                    # 敏感词过滤（DFA）
│   └── src/main/resources/
│       ├── application.yml
│       ├── application-prod.yml
│       ├── mapper/                    # MyBatis XML
│       └── sensitive-words.txt        # 敏感词库
│
├── luyingdazi-mp/                     # 微信小程序前端
│   ├── project.config.json
│   ├── app.js
│   ├── app.json
│   ├── app.wxss
│   ├── pages/
│   │   ├── index/                     # 首页（动态流）
│   │   ├── discover/                  # 发现（同城匹配）
│   │   ├── activity/                  # 活动（组队列表）
│   │   ├── message/                   # 消息（私信列表）
│   │   └── mine/                      # 我的（个人中心）
│   ├── subpages/
│   │   ├── post-detail/               # 动态详情
│   │   ├── post-publish/              # 发布动态
│   │   ├── chat/                      # 聊天页
│   │   ├── activity-detail/           # 活动详情
│   │   ├── activity-create/           # 发起活动
│   │   ├── user-profile/              # 用户主页
│   │   └── edit-profile/              # 编辑资料
│   ├── components/                    # 公共组件
│   ├── utils/                         # 工具函数（请求封装/时间/地图）
│   └── static/                        # 静态资源
│
└── luyingdazi-admin/                  # 管理后台（第二版再做）
    └── ...
```

---

## 五、数据库设计（MySQL）

### 5.1 核心表结构

```sql
-- ============================================================
-- 数据库：luyingdazi
-- 字符集：utf8mb4
-- ============================================================

CREATE DATABASE IF NOT EXISTS `luyingdazi` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `luyingdazi`;

-- -----------------------------------------------------------
-- 1. 用户表
-- -----------------------------------------------------------
CREATE TABLE `t_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `openid` VARCHAR(128) NOT NULL COMMENT '微信小程序openid',
    `unionid` VARCHAR(128) DEFAULT NULL COMMENT '微信unionid',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号（微信授权获取）',
    `nickname` VARCHAR(50) NOT NULL DEFAULT '露营新人' COMMENT '昵称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `gender` TINYINT DEFAULT 0 COMMENT '性别：0未知 1男 2女',
    `birthday` DATE DEFAULT NULL COMMENT '生日',
    `city` VARCHAR(50) DEFAULT NULL COMMENT '所在城市',
    `province` VARCHAR(50) DEFAULT NULL COMMENT '省份',
    `bio` VARCHAR(200) DEFAULT NULL COMMENT '个人简介',
    `camping_years` TINYINT DEFAULT 0 COMMENT '露营年限：0新手 1一年内 2一到三年 3三年以上',
    `credit_score` INT DEFAULT 100 COMMENT '信用评分（满分100）',
    `member_level` TINYINT DEFAULT 0 COMMENT '会员等级：0普通 1月度 2季度 3年度',
    `member_expire_time` DATETIME DEFAULT NULL COMMENT '会员到期时间',
    `invite_code` VARCHAR(10) DEFAULT NULL COMMENT '我的邀请码',
    `invited_by` BIGINT DEFAULT NULL COMMENT '邀请人ID',
    `coin_balance` BIGINT DEFAULT 0 COMMENT '金币余额',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0封禁 1正常',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_openid` (`openid`),
    KEY `idx_city` (`city`),
    KEY `idx_location` (`latitude`, `longitude`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- -----------------------------------------------------------
-- 2. 用户露营标签表
-- -----------------------------------------------------------
CREATE TABLE `t_user_tag` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `tag_name` VARCHAR(20) NOT NULL COMMENT '标签（如：自驾露营/徒步露营/烧烤/星空摄影/亲子）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_tag_name` (`tag_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户露营标签表';

-- -----------------------------------------------------------
-- 3. 关注关系表
-- -----------------------------------------------------------
CREATE TABLE `t_follow` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '关注者',
    `follow_user_id` BIGINT NOT NULL COMMENT '被关注者',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_follow` (`user_id`, `follow_user_id`),
    KEY `idx_follow_user` (`follow_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关注关系表';

-- -----------------------------------------------------------
-- 4. 动态表
-- -----------------------------------------------------------
CREATE TABLE `t_post` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '动态ID',
    `user_id` BIGINT NOT NULL COMMENT '发布者',
    `content` TEXT COMMENT '文字内容',
    `images` JSON DEFAULT NULL COMMENT '图片URL数组',
    `location_name` VARCHAR(100) DEFAULT NULL COMMENT '定位名称',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `comment_count` INT DEFAULT 0 COMMENT '评论数',
    `is_top` TINYINT DEFAULT 0 COMMENT '是否置顶',
    `top_expire_time` DATETIME DEFAULT NULL COMMENT '置顶到期时间',
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
    `post_id` BIGINT NOT NULL COMMENT '动态ID',
    `user_id` BIGINT NOT NULL COMMENT '评论者',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父评论ID（0=一级）',
    `reply_user_id` BIGINT DEFAULT NULL COMMENT '回复谁',
    `content` VARCHAR(500) NOT NULL COMMENT '内容',
    `status` TINYINT DEFAULT 1 COMMENT '0删除 1正常',
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
    `target_id` BIGINT NOT NULL COMMENT '目标ID',
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
    `user_a_id` BIGINT NOT NULL COMMENT '用户A（ID较小的）',
    `user_b_id` BIGINT NOT NULL COMMENT '用户B（ID较大的）',
    `last_msg` VARCHAR(100) DEFAULT NULL COMMENT '最后消息摘要',
    `last_msg_time` DATETIME DEFAULT NULL,
    `a_unread` INT DEFAULT 0 COMMENT 'A的未读数',
    `b_unread` INT DEFAULT 0 COMMENT 'B的未读数',
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
    `sender_id` BIGINT NOT NULL COMMENT '发送者',
    `receiver_id` BIGINT NOT NULL COMMENT '接收者',
    `msg_type` TINYINT DEFAULT 1 COMMENT '1文本 2图片 3位置',
    `content` TEXT COMMENT '消息内容',
    `is_read` TINYINT DEFAULT 0,
    `status` TINYINT DEFAULT 1 COMMENT '0撤回 1正常',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_session` (`session_id`, `created_at`),
    KEY `idx_receiver` (`receiver_id`, `is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私信消息表';

-- -----------------------------------------------------------
-- 9. 活动表（组队露营）
-- -----------------------------------------------------------
CREATE TABLE `t_activity` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL COMMENT '发起人',
    `title` VARCHAR(50) NOT NULL COMMENT '标题',
    `description` TEXT COMMENT '描述',
    `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图',
    `location_name` VARCHAR(100) NOT NULL COMMENT '地点名称',
    `longitude` DECIMAL(10,6) DEFAULT NULL,
    `latitude` DECIMAL(10,6) DEFAULT NULL,
    `start_time` DATETIME NOT NULL COMMENT '开始时间',
    `end_time` DATETIME NOT NULL COMMENT '结束时间',
    `max_members` INT DEFAULT 0 COMMENT '最大人数（0不限）',
    `current_members` INT DEFAULT 1 COMMENT '当前人数',
    `fee_desc` VARCHAR(100) DEFAULT '免费' COMMENT '费用说明（如AA/免费/人均xx）',
    `requirement` VARCHAR(200) DEFAULT NULL COMMENT '参与要求',
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
    `reporter_id` BIGINT NOT NULL COMMENT '举报人',
    `target_id` BIGINT NOT NULL COMMENT '被举报目标ID',
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
```

### 5.2 第二版新增表（开通支付后）

```sql
-- 会员订单表
CREATE TABLE `t_member_order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单号',
    `user_id` BIGINT NOT NULL,
    `member_type` TINYINT NOT NULL COMMENT '1月度 2季度 3年度',
    `amount` INT NOT NULL COMMENT '金额（分）',
    `trade_no` VARCHAR(64) DEFAULT NULL COMMENT '微信支付单号',
    `status` TINYINT DEFAULT 0 COMMENT '0待付 1已付 2退款',
    `paid_at` DATETIME DEFAULT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员订单表';

-- 虚拟礼物表
CREATE TABLE `t_gift` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL COMMENT '礼物名称',
    `icon` VARCHAR(500) NOT NULL COMMENT '图标URL',
    `price` INT NOT NULL COMMENT '价格（金币）',
    `sort_order` INT DEFAULT 0,
    `status` TINYINT DEFAULT 1,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='虚拟礼物表';

-- 礼物记录表
CREATE TABLE `t_gift_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `sender_id` BIGINT NOT NULL,
    `receiver_id` BIGINT NOT NULL,
    `gift_id` BIGINT NOT NULL,
    `gift_count` INT DEFAULT 1,
    `total_coin` BIGINT NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_sender` (`sender_id`),
    KEY `idx_receiver` (`receiver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='礼物记录表';

-- 邀请记录表
CREATE TABLE `t_invite_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `inviter_id` BIGINT NOT NULL,
    `invitee_id` BIGINT NOT NULL,
    `reward_days` INT DEFAULT 3 COMMENT '奖励会员天数',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_invitee` (`invitee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邀请记录表';
```

---

## 六、Redis 缓存设计

### 6.1 配置（低内存优化）

```yaml
# redis.conf 核心配置
maxmemory 128mb
maxmemory-policy allkeys-lru
save ""
# 关闭 RDB 持久化，用 AOF 或不持久化（省磁盘IO）
appendonly no
```

### 6.2 Key 设计

```
命名规则：lyd:{模块}:{功能}:{标识}
```

| Key | 类型 | TTL | 用途 |
|-----|------|-----|------|
| `lyd:user:token:{token}` | String(userId) | 7天 | 登录态 |
| `lyd:user:info:{userId}` | Hash | 30分钟 | 用户信息缓存 |
| `lyd:user:loc:{city}` | GEO | 30分钟 | 同城用户位置（GEORADIUS 匹配） |
| `lyd:post:like:{postId}` | Set(userIds) | 持久 | 动态点赞用户集合 |
| `lyd:post:hot:{city}` | ZSet | 1小时 | 同城热门动态 |
| `lyd:chat:unread:{userId}` | Hash(sessionId→count) | 持久 | 未读消息数 |
| `lyd:limit:chat:{userId}` | Set(targetIds) | 到当日24点 | 每日私信限制（免费3人） |
| `lyd:limit:match:{userId}` | String(count) | 到当日24点 | 每日匹配刷新限制 |
| `lyd:ws:online:{userId}` | String(1) | 5分钟 | 在线状态（心跳续期） |

### 6.3 同城匹配核心逻辑

```
# 用户上报位置 → 写入 GEO
GEOADD lyd:user:loc:上海 121.4737 31.2304 userId_123

# 查找附近 10km 内的 20 个用户
GEORADIUS lyd:user:loc:上海 121.47 31.23 10 km COUNT 20 ASC

# 再用标签匹配做二次排序（业务层处理）
```

---

## 七、现有资源与部署计划

### 7.1 已有资源

| 资源 | 详情 | 状态 |
|------|------|------|
| ECS 服务器 | 2核2G / 上海 / 公网 8.153.18.148 / 3Mbps | ✅ 运行中，到期 2026-10-28 |
| OSS | 已有 1 个 Bucket | ✅ 可用 |
| 安全组 | 3 个 | ✅ 已有 |

### 7.2 部署步骤

```bash
# 1. SSH 登录服务器
ssh root@8.153.18.148

# 2. 安装基础环境
yum install -y docker docker-compose
# 或直接安装 MySQL + Redis + JDK17 + Nginx

# 3. 配置 MySQL（低内存）
# innodb_buffer_pool_size = 256M
# max_connections = 50

# 4. 配置 Redis（低内存）
# maxmemory 128mb

# 5. 配置 Swap（防 OOM）
fallocate -l 1G /swapfile
chmod 600 /swapfile
mkswap /swapfile
swapon /swapfile

# 6. 部署 Spring Boot jar
nohup java -Xms256m -Xmx384m -jar luyingdazi-server.jar --spring.profiles.active=prod &

# 7. 配置 Nginx（反向代理 + SSL）
# 小程序要求 HTTPS，需配置 SSL 证书（Let's Encrypt 免费）
```

### 7.3 小程序 HTTPS 要求

微信小程序要求后端接口必须是 HTTPS，两种免费方案：

| 方案 | 说明 |
|------|------|
| **方案A：买个便宜域名** | ¥9-30/年 + Let's Encrypt 免费 SSL |
| **方案B：阿里云免费证书** | 绑定域名后可申请免费 DV 证书（1年） |

> 注意：微信不允许直接用 IP 地址，必须有域名。所以域名是唯一必须花钱的地方，大约 ¥10-30/年。

---

## 八、成本总结

### 当前阶段（MVP 验证期）

| 项目 | 费用 | 说明 |
|------|------|------|
| 服务器 | ¥0 | 已有 ECS，到期 2026-10 |
| OSS 存储 | ¥0-5/月 | 已有 Bucket，按量极低 |
| 域名 | ¥10-30/年 | 唯一必须花钱的（小程序要 HTTPS） |
| SSL 证书 | ¥0 | Let's Encrypt 免费 |
| 小程序注册 | ¥0 | 个人主体免费 |
| 登录认证 | ¥0 | 微信 wx.login 免费 |
| 内容安全 | ¥0 | 微信 msgSecCheck 免费 |
| 消息推送 | ¥0 | 微信订阅消息免费 |
| 地图定位 | ¥0 | wx.getLocation 免费 |
| **年度总计** | **¥10-65/年** | |

### 有收入后升级（企业主体）

| 项目 | 费用 | 说明 |
|------|------|------|
| 个体工商户注册 | ¥0-500（一次性） | 各地政策不同 |
| 小程序企业认证 | ¥300/年 | 开通支付能力 |
| 微信支付手续费 | 交易额 × 0.6% | 有收入才有此项 |
| **额外年度成本** | **¥300-800/年** | |

---

## 九、开发排期

### MVP 第一版（个人主体，4-6 周）

| 周次 | 后端任务 | 前端任务 |
|------|----------|----------|
| 第1周 | 项目搭建 + 微信登录 + 用户 CRUD | 小程序初始化 + 登录流程 + 个人中心 |
| 第2周 | 动态发布/列表/详情 + 评论点赞 | 首页动态流 + 发布页 + 详情页 |
| 第3周 | WebSocket 私信 + 会话管理 | 消息列表 + 聊天页 |
| 第4周 | 同城匹配（GEO）+ 关注/粉丝 | 发现页 + 用户主页 |
| 第5周 | 活动组队（发起/报名/列表） | 活动列表 + 详情 + 发起页 |
| 第6周 | 敏感词过滤 + 举报 + 联调部署 | 联调 + 体验优化 + 提审 |

### 第二版（注册企业主体后，2-3 周）

- 微信支付接入
- 会员系统
- 虚拟礼物
- 邀请裂变

---

## 十、微信小程序个人主体限制

| 能力 | 个人主体 | 企业主体 |
|------|----------|----------|
| 微信登录（wx.login） | ✅ | ✅ |
| 获取手机号 | ❌ | ✅ |
| 微信支付 | ❌ | ✅ |
| 订阅消息 | ✅（一次性） | ✅（一次性+长期） |
| 地图定位 | ✅ | ✅ |
| 内容安全 API | ✅ | ✅ |
| 类目选择 | 有限 | 全部 |

**影响**：个人主体不能收钱、不能获取手机号。但社交核心功能（发动态、聊天、组队、匹配）全部可用，足够验证产品。

---

## 十一、风险与注意事项

1. **小程序审核**：社交类小程序审核较严，内容安全必须接好，否则会被拒
2. **内存管理**：2G 内存紧张，必须严格控制各组件内存上限，加 Swap 兜底
3. **WebSocket 并发**：2核能支撑约 500-1000 并发连接，MVP 阶段足够
4. **防刷**：做好接口限流（Redis 令牌桶），防止恶意请求打满服务器
5. **数据备份**：MySQL 定时 mysqldump 到 OSS，防止数据丢失
6. **域名备案**：使用阿里云服务器 + 域名需要 ICP 备案（约 1-2 周）

---

## 十二、快速启动

```bash
# 1. 克隆项目
git clone https://github.com/yourname/luyingdazi.git
cd luyingdazi

# 2. 后端启动（本地开发）
cd luyingdazi-server
mvn clean package -DskipTests
java -jar target/luyingdazi-server.jar --spring.profiles.active=dev

# 3. 小程序开发
cd luyingdazi-mp
# 用微信开发者工具打开此目录
```

---

*文档版本：v2.0 | 更新时间：2026-06-19 | 策略：0成本 MVP 验证*

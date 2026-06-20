# 🏕️ 野趣搭子 — 户外露营垂直社交平台

> 一款专为露营爱好者打造的垂直社交平台，帮助用户找到志同道合的露营搭子，组队出行，分享户外生活。

---

## 一、项目概述

### 1.1 产品定位

**野趣搭子**是一款面向 18-45 岁城市年轻人的户外露营垂直社交平台，核心解决以下痛点：

| 痛点 | 解决方案 |
|------|----------|
| 想露营但找不到同伴 | 基于 LBS 的同城智能匹配 + 组队系统 |
| 不知道去哪里露营 | UGC 图文动态 + 营地推荐 |
| 露营装备不全、经验不足 | 社区交流 + 商户入驻装备租赁 |
| 安全顾虑（陌生人社交） | 人脸实名认证 + 信用评分体系 |
| 社交平台信息太杂 | 垂直领域精准匹配 |

### 1.2 目标用户画像

- **核心用户**：22-35 岁一二线城市白领，周末有户外出行需求
- **次核心用户**：18-25 岁大学生、户外运动爱好者
- **延伸用户**：35-45 岁亲子露营家庭、户外装备商户

---

## 二、竞品分析

### 2.1 市场现有产品调研

| 产品名称 | 平台 | 核心功能 | 盈利模式 | 优势 | 不足 |
|----------|------|----------|----------|------|------|
| **营派** | iOS/Android | 户外旅游交友、组队露营 | 会员订阅 | 垂直露营领域先发 | 用户基数小，社交功能弱 |
| **搭子组局** | iOS/Android | 兴趣结伴（含露营）、活动组局 | 活动抽佣+会员 | 活动品类丰富 | 非垂直，露营深度不足 |
| **搭da哒** | iOS/Android | 兴趣搭子活动社区 | 会员+广告 | 年轻人定位清晰 | 通用社交，缺少户外专业内容 |
| **哼搭** | iOS/Android | AI 大数据兴趣匹配 | 会员+虚拟礼物 | AI 匹配精准 | 泛社交，露营场景覆盖弱 |
| **都趣搭** | iOS/Android | 智能匹配找搭子 | 会员订阅 | 匹配算法好 | 缺少线下活动组织能力 |
| **积目** | iOS/Android | 年轻人扩圈交友 | 会员+直播打赏 | 用户量大、社交成熟 | 非垂直领域，鱼龙混杂 |
| **小红书** | iOS/Android/小程序 | 露营攻略分享（非社交） | 电商+广告 | 内容生态强 | 无组队功能，无即时通讯 |

### 2.2 竞争差异化定位

相比上述产品，**野趣搭子**的差异化优势：

1. **垂直到露营**：不做泛兴趣社交，只聚焦户外露营场景
2. **线上+线下闭环**：从匹配→组队→出行→分享，完整链路
3. **安全可信**：人脸实名 + 信用评分 + 出行保险
4. **商户生态**：营地预订、装备租赁、向导服务一站式
5. **活动抽佣+会员双引擎**：比纯会员模式天花板更高

---

## 三、功能模块设计（免费 + 付费）

### 3.1 用户模块

| 功能 | 免费用户 | 付费会员 |
|------|----------|----------|
| 手机号注册/微信一键登录 | ✅ | ✅ |
| 人脸实名认证 | ✅ | ✅ |
| 个人资料编辑（头像/昵称/露营标签） | ✅ | ✅ |
| 关注/粉丝 | ✅ | ✅ |
| 访客记录查看 | 最近3人 | 全部+隐身访问 |
| 个人主页装扮 | 基础模板 | 自定义主题 |

### 3.2 社交模块

| 功能 | 免费用户 | 付费会员 |
|------|----------|----------|
| 图文动态发布（最多9图+定位） | ✅ | ✅ |
| 评论/点赞/收藏 | ✅ | ✅ |
| 同城用户推荐（默认10km） | 每天5次刷新 | 无限刷新+扩大范围 |
| 一对一私信聊天 | 每天3人 | 无限制 |
| 查看对方在线状态 | ❌ | ✅ |
| 动态置顶（同城热门） | ❌ | 付费置顶（单次/包月） |
| 超级喜欢（优先推荐给对方） | ❌ | 每天3次 |

### 3.3 活动模块

| 功能 | 免费用户 | 付费会员 |
|------|----------|----------|
| 浏览组队活动 | ✅ | ✅ |
| 加入组队 | ✅ | 优先报名 |
| 发起组队活动 | ✅ | 置顶曝光 |
| 活动评价/打分 | ✅ | ✅ |
| 活动保险购买 | ✅（自费） | 赠送基础险 |

### 3.4 盈利模块详细设计

#### 💰 会员订阅

| 套餐 | 价格 | 权益 |
|------|------|------|
| 月度会员 | ¥28/月 | 无限私信+访客记录+在线状态+每日3次超级喜欢 |
| 季度会员 | ¥68/季（¥22.7/月） | 月度权益+个人主页装扮+活动优先报名 |
| 年度会员 | ¥198/年（¥16.5/月） | 季度权益+赠送活动保险+专属客服+年度徽章 |

#### 🎁 虚拟礼物打赏

- 礼物类型：帐篷（1元）、篝火（5元）、星空（10元）、房车（50元）、极光（99元）
- 分成比例：平台抽30%，用户得70%（可提现）
- 用途场景：给喜欢的动态/用户打赏，提升曝光权重

#### 🏕️ 线下活动抽佣

- 活动发起人设置参与费用（如 AA 制装备费+场地费）
- 平台抽佣 5%-10%（根据活动规模阶梯递减）
- 商户发起的商业活动抽佣 15%

#### 📌 动态置顶付费

- 同城热门置顶：¥5/24小时
- 全国热门推荐：¥20/24小时
- 会员享 5 折优惠

#### 🏪 商户入驻年费

| 商户类型 | 年费 | 权益 |
|----------|------|------|
| 营地方 | ¥2,000/年 | 营地展示页+活动发布+用户引流 |
| 装备商 | ¥3,000/年 | 商品橱窗+动态广告位+精准推送 |
| 向导/教练 | ¥1,000/年 | 个人认证+优先推荐+接单系统 |

### 3.5 配套工具

| 工具 | 说明 |
|------|------|
| 敏感词过滤 | 基于 DFA 算法 + 第三方内容安全 API（阿里/腾讯），覆盖文字+图片 |
| 邀请裂变系统 | 邀请码机制，邀请成功送3天会员体验，被邀请人首充返10% |
| 微信/支付宝支付 | 完整支付+退款回调链路，支持 H5/小程序/App 多端 |
| 会员权限拦截 | 基于 AOP 的接口级权限控制，非会员触发付费引导弹窗 |
| 推送通知 | 极光推送/UniPush，覆盖私信、评论、活动提醒等场景 |
| 举报与封禁 | 用户举报→人工审核→违规处罚（禁言/封号）三级体系 |

---

## 四、技术架构设计

### 4.1 技术选型

| 层级 | 技术方案 | 说明 |
|------|----------|------|
| **前端（小程序/App/H5）** | UniApp + Vue3 + TypeScript | 一套代码多端运行 |
| **UI 框架** | uView Plus / uni-ui | 成熟的 UniApp 组件库 |
| **后端框架** | Spring Boot 3.x + JDK 17 | 主流 Java 微服务框架 |
| **数据库** | MySQL 8.0 | 主业务数据存储 |
| **缓存** | Redis 7.x | 会话/热数据/排行/限流 |
| **搜索引擎** | Elasticsearch 8.x | 动态全文检索/用户搜索 |
| **即时通讯** | WebSocket + Netty | 低延迟私信聊天 |
| **消息队列** | RabbitMQ / RocketMQ | 异步处理（通知/积分/统计） |
| **对象存储** | 阿里云 OSS / 腾讯云 COS | 图片/视频存储 |
| **人脸认证** | 阿里云实人认证 / 百度人脸核身 | 活体检测+身份证比对 |
| **内容审核** | 阿里云内容安全 / 腾讯天御 | 文本+图片敏感内容识别 |
| **支付** | 微信支付 + 支付宝 | APP/小程序/H5 全渠道 |
| **地图定位** | 高德地图 SDK | LBS 同城匹配+活动定位 |
| **推送** | 极光推送 / UniPush | 消息触达 |
| **部署** | Docker + Nginx + 阿里云ECS | 容器化部署 |
| **CI/CD** | Jenkins / GitHub Actions | 自动化构建部署 |

### 4.2 系统架构图

```
┌──────────────────────────────────────────────────────────┐
│                    客户端（多端）                          │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌─────────┐ │
│  │ 微信小程序 │  │  App iOS │  │ App Android│  │   H5    │ │
│  └──────────┘  └──────────┘  └──────────┘  └─────────┘ │
│                  UniApp + Vue3 + TypeScript               │
└─────────────────────────┬────────────────────────────────┘
                          │ HTTPS
┌─────────────────────────▼────────────────────────────────┐
│                    Nginx 网关层                            │
│          负载均衡 / SSL终止 / 静态资源 / 限流              │
└─────────────────────────┬────────────────────────────────┘
                          │
┌─────────────────────────▼────────────────────────────────┐
│                Spring Boot 应用服务层                      │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌──────────┐      │
│  │用户服务  │ │社交服务  │ │支付服务  │ │IM即时通讯 │      │
│  └─────────┘ └─────────┘ └─────────┘ └──────────┘      │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌──────────┐      │
│  │活动服务  │ │商户服务  │ │内容审核  │ │通知推送   │      │
│  └─────────┘ └─────────┘ └─────────┘ └──────────┘      │
└───┬──────────────┬──────────────┬────────────────────────┘
    │              │              │
┌───▼───┐   ┌─────▼─────┐  ┌────▼────┐
│MySQL 8│   │  Redis 7   │  │   ES 8  │
│主从复制│   │哨兵/Cluster│  │全文检索  │
└───────┘   └───────────┘  └─────────┘
    │              │
┌───▼──────────────▼───────────────────┐
│          消息队列 (RabbitMQ)           │
│   异步通知 / 积分变更 / 数据统计       │
└──────────────────────────────────────┘
    │
┌───▼──────────────────────────────────┐
│          第三方服务集成                 │
│  阿里云OSS / 人脸认证 / 内容安全       │
│  微信支付 / 支付宝 / 高德地图 / 极光   │
└──────────────────────────────────────┘
```

### 4.3 项目目录结构

```
luyingdazi/
├── README.md                          # 项目说明文档（本文件）
├── doc/                               # 文档目录
│   ├── sql/                           # 数据库脚本
│   │   ├── init_schema.sql            # 建库建表 DDL
│   │   ├── init_data.sql              # 初始化数据（敏感词、系统配置等）
│   │   └── upgrade/                   # 增量升级脚本
│   ├── api/                           # 接口文档
│   │   └── openapi.yaml              # OpenAPI 3.0 规范
│   ├── design/                        # 设计文档
│   │   ├── 产品需求文档PRD.md
│   │   ├── 数据库ER图.md
│   │   └── 接口设计文档.md
│   └── deploy/                        # 部署文档
│       ├── docker-compose.yml         # Docker 编排
│       ├── nginx.conf                 # Nginx 配置
│       └── deploy-guide.md            # 部署指南
│
├── luyingdazi-server/                 # 后端 Spring Boot 项目
│   ├── pom.xml                        # Maven 父 POM
│   ├── luyingdazi-common/             # 公共模块（工具类/常量/异常）
│   │   └── src/main/java/com/luyingdazi/common/
│   │       ├── constant/              # 常量定义
│   │       ├── enums/                 # 枚举类
│   │       ├── exception/             # 自定义异常
│   │       ├── util/                  # 工具类
│   │       └── result/                # 统一响应封装
│   │
│   ├── luyingdazi-model/              # 数据模型（Entity/DTO/VO/Query）
│   │   └── src/main/java/com/luyingdazi/model/
│   │       ├── entity/                # 数据库实体
│   │       ├── dto/                   # 数据传输对象
│   │       ├── vo/                    # 视图对象
│   │       └── query/                 # 查询参数对象
│   │
│   ├── luyingdazi-mapper/             # 数据访问层（MyBatis Mapper）
│   │   └── src/main/
│   │       ├── java/com/luyingdazi/mapper/
│   │       └── resources/mapper/      # MyBatis XML
│   │
│   ├── luyingdazi-service/            # 业务逻辑层
│   │   └── src/main/java/com/luyingdazi/service/
│   │       ├── user/                  # 用户服务
│   │       ├── social/                # 社交服务
│   │       ├── im/                    # 即时通讯服务
│   │       ├── activity/              # 活动服务
│   │       ├── payment/               # 支付服务
│   │       ├── member/                # 会员服务
│   │       ├── gift/                  # 虚拟礼物服务
│   │       ├── merchant/              # 商户服务
│   │       ├── content/               # 内容审核服务
│   │       └── notification/          # 通知推送服务
│   │
│   ├── luyingdazi-api/                # 接口层（Controller + 启动类）
│   │   └── src/main/
│   │       ├── java/com/luyingdazi/api/
│   │       │   ├── controller/        # REST 接口
│   │       │   ├── config/            # Spring 配置
│   │       │   ├── interceptor/       # 拦截器（登录/会员权限）
│   │       │   ├── filter/            # 过滤器
│   │       │   ├── websocket/         # WebSocket 配置
│   │       │   └── LuyingdaziApplication.java
│   │       └── resources/
│   │           ├── application.yml
│   │           ├── application-dev.yml
│   │           ├── application-prod.yml
│   │           └── logback-spring.xml
│   │
│   └── luyingdazi-job/                # 定时任务模块
│       └── src/main/java/com/luyingdazi/job/
│           ├── MemberExpireJob.java    # 会员到期处理
│           ├── DataStatisticsJob.java  # 数据统计
│           └── ContentCleanJob.java    # 过期内容清理
│
├── luyingdazi-app/                    # 前端 UniApp 项目
│   ├── package.json
│   ├── manifest.json                  # UniApp 配置
│   ├── pages.json                     # 页面路由配置
│   ├── App.vue
│   ├── main.ts
│   ├── uni.scss
│   ├── static/                        # 静态资源
│   ├── api/                           # 接口请求封装
│   │   ├── request.ts                 # HTTP 请求工具
│   │   ├── user.ts                    # 用户接口
│   │   ├── social.ts                  # 社交接口
│   │   ├── im.ts                      # IM 接口
│   │   ├── activity.ts                # 活动接口
│   │   └── payment.ts                 # 支付接口
│   ├── pages/                         # 页面
│   │   ├── index/                     # 首页（推荐动态流）
│   │   ├── discover/                  # 发现（同城匹配）
│   │   ├── activity/                  # 活动（组队列表）
│   │   ├── message/                   # 消息（私信列表）
│   │   └── mine/                      # 我的（个人中心）
│   ├── subpages/                      # 分包页面
│   │   ├── user/                      # 用户相关页面
│   │   ├── post/                      # 动态相关页面
│   │   ├── chat/                      # 聊天页面
│   │   ├── activity/                  # 活动详情页面
│   │   ├── payment/                   # 支付相关页面
│   │   └── merchant/                  # 商户页面
│   ├── components/                    # 公共组件
│   ├── store/                         # Pinia 状态管理
│   ├── utils/                         # 工具函数
│   └── hooks/                         # 组合式函数
│
└── luyingdazi-admin/                  # 管理后台（Vue3 + Element Plus）
    ├── package.json
    ├── src/
    │   ├── views/
    │   │   ├── user/                  # 用户管理
    │   │   ├── content/               # 内容审核
    │   │   ├── activity/              # 活动管理
    │   │   ├── payment/               # 财务管理
    │   │   ├── merchant/              # 商户管理
    │   │   ├── system/                # 系统设置
    │   │   └── statistics/            # 数据统计
    │   └── ...
    └── ...
```

---

## 五、数据库设计（MySQL）

### 5.1 核心表结构

以下为主要数据表设计，完整 DDL 见 `doc/sql/init_schema.sql`

```sql
-- ============================================================
-- 数据库：luyingdazi
-- 字符集：utf8mb4
-- 排序规则：utf8mb4_general_ci
-- ============================================================

CREATE DATABASE IF NOT EXISTS `luyingdazi` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `luyingdazi`;

-- -----------------------------------------------------------
-- 1. 用户表
-- -----------------------------------------------------------
CREATE TABLE `t_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `password` VARCHAR(128) DEFAULT NULL COMMENT '密码（加密）',
    `nickname` VARCHAR(50) NOT NULL COMMENT '昵称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `gender` TINYINT DEFAULT 0 COMMENT '性别：0未知 1男 2女',
    `birthday` DATE DEFAULT NULL COMMENT '生日',
    `city` VARCHAR(50) DEFAULT NULL COMMENT '所在城市',
    `province` VARCHAR(50) DEFAULT NULL COMMENT '省份',
    `bio` VARCHAR(200) DEFAULT NULL COMMENT '个人简介',
    `wechat_openid` VARCHAR(128) DEFAULT NULL COMMENT '微信OpenID',
    `wechat_unionid` VARCHAR(128) DEFAULT NULL COMMENT '微信UnionID',
    `is_verified` TINYINT DEFAULT 0 COMMENT '是否实名认证：0否 1是',
    `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名（加密存储）',
    `id_card_no` VARCHAR(128) DEFAULT NULL COMMENT '身份证号（加密存储）',
    `credit_score` INT DEFAULT 100 COMMENT '信用评分（满分100）',
    `member_level` TINYINT DEFAULT 0 COMMENT '会员等级：0普通 1月度 2季度 3年度',
    `member_expire_time` DATETIME DEFAULT NULL COMMENT '会员到期时间',
    `invite_code` VARCHAR(20) DEFAULT NULL COMMENT '邀请码',
    `invited_by` BIGINT DEFAULT NULL COMMENT '邀请人ID',
    `coin_balance` BIGINT DEFAULT 0 COMMENT '金币余额（1元=100金币）',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0禁用 1正常 2封号',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_phone` (`phone`),
    UNIQUE KEY `uk_invite_code` (`invite_code`),
    KEY `idx_wechat_openid` (`wechat_openid`),
    KEY `idx_city` (`city`),
    KEY `idx_member_level` (`member_level`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- -----------------------------------------------------------
-- 2. 用户标签表（露营偏好）
-- -----------------------------------------------------------
CREATE TABLE `t_user_tag` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `tag_name` VARCHAR(30) NOT NULL COMMENT '标签名称',
    `tag_type` TINYINT DEFAULT 1 COMMENT '标签类型：1露营风格 2装备水平 3出行偏好',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户标签表';

-- -----------------------------------------------------------
-- 3. 关注关系表
-- -----------------------------------------------------------
CREATE TABLE `t_user_follow` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '关注者ID',
    `follow_user_id` BIGINT NOT NULL COMMENT '被关注者ID',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '关注时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_follow` (`user_id`, `follow_user_id`),
    KEY `idx_follow_user_id` (`follow_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关注关系表';

-- -----------------------------------------------------------
-- 4. 访客记录表
-- -----------------------------------------------------------
CREATE TABLE `t_visitor_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '被访问者ID',
    `visitor_id` BIGINT NOT NULL COMMENT '访客ID',
    `visit_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id_time` (`user_id`, `visit_time` DESC),
    KEY `idx_visitor_id` (`visitor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访客记录表';

-- -----------------------------------------------------------
-- 5. 动态表（图文帖子）
-- -----------------------------------------------------------
CREATE TABLE `t_post` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '动态ID',
    `user_id` BIGINT NOT NULL COMMENT '发布者ID',
    `content` TEXT COMMENT '文字内容',
    `images` VARCHAR(2000) DEFAULT NULL COMMENT '图片URL列表（JSON数组）',
    `video_url` VARCHAR(500) DEFAULT NULL COMMENT '视频URL',
    `location_name` VARCHAR(100) DEFAULT NULL COMMENT '定位名称',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `topic_id` BIGINT DEFAULT NULL COMMENT '话题ID',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `comment_count` INT DEFAULT 0 COMMENT '评论数',
    `share_count` INT DEFAULT 0 COMMENT '分享数',
    `is_top` TINYINT DEFAULT 0 COMMENT '是否置顶：0否 1是',
    `top_expire_time` DATETIME DEFAULT NULL COMMENT '置顶到期时间',
    `visibility` TINYINT DEFAULT 1 COMMENT '可见范围：1公开 2仅粉丝 3私密',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0删除 1正常 2审核中 3违规下架',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_created_at` (`created_at` DESC),
    KEY `idx_location` (`latitude`, `longitude`),
    KEY `idx_topic_id` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='动态表';

-- -----------------------------------------------------------
-- 6. 评论表
-- -----------------------------------------------------------
CREATE TABLE `t_comment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `post_id` BIGINT NOT NULL COMMENT '动态ID',
    `user_id` BIGINT NOT NULL COMMENT '评论者ID',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父评论ID（0为一级评论）',
    `reply_user_id` BIGINT DEFAULT NULL COMMENT '回复对象用户ID',
    `content` VARCHAR(500) NOT NULL COMMENT '评论内容',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0删除 1正常',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
    PRIMARY KEY (`id`),
    KEY `idx_post_id` (`post_id`, `created_at`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- -----------------------------------------------------------
-- 7. 点赞表
-- -----------------------------------------------------------
CREATE TABLE `t_like` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `target_id` BIGINT NOT NULL COMMENT '目标ID',
    `target_type` TINYINT NOT NULL COMMENT '目标类型：1动态 2评论',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_target` (`user_id`, `target_id`, `target_type`),
    KEY `idx_target` (`target_id`, `target_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';

-- -----------------------------------------------------------
-- 8. 私信会话表
-- -----------------------------------------------------------
CREATE TABLE `t_chat_session` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '会话ID',
    `user_id` BIGINT NOT NULL COMMENT '用户A的ID（较小的ID）',
    `target_user_id` BIGINT NOT NULL COMMENT '用户B的ID（较大的ID）',
    `last_msg_content` VARCHAR(200) DEFAULT NULL COMMENT '最后一条消息摘要',
    `last_msg_time` DATETIME DEFAULT NULL COMMENT '最后消息时间',
    `user_unread_count` INT DEFAULT 0 COMMENT '用户A未读数',
    `target_unread_count` INT DEFAULT 0 COMMENT '用户B未读数',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0删除 1正常',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_session` (`user_id`, `target_user_id`),
    KEY `idx_target_user_id` (`target_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私信会话表';

-- -----------------------------------------------------------
-- 9. 私信消息表
-- -----------------------------------------------------------
CREATE TABLE `t_chat_message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    `session_id` BIGINT NOT NULL COMMENT '会话ID',
    `sender_id` BIGINT NOT NULL COMMENT '发送者ID',
    `receiver_id` BIGINT NOT NULL COMMENT '接收者ID',
    `msg_type` TINYINT DEFAULT 1 COMMENT '消息类型：1文本 2图片 3语音 4位置 5礼物',
    `content` TEXT COMMENT '消息内容',
    `is_read` TINYINT DEFAULT 0 COMMENT '是否已读：0否 1是',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0撤回 1正常',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    PRIMARY KEY (`id`),
    KEY `idx_session_id` (`session_id`, `created_at`),
    KEY `idx_receiver_id` (`receiver_id`, `is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私信消息表';

-- -----------------------------------------------------------
-- 10. 活动表（组队露营）
-- -----------------------------------------------------------
CREATE TABLE `t_activity` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '活动ID',
    `user_id` BIGINT NOT NULL COMMENT '发起人ID',
    `title` VARCHAR(100) NOT NULL COMMENT '活动标题',
    `description` TEXT COMMENT '活动描述',
    `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图',
    `activity_type` TINYINT DEFAULT 1 COMMENT '活动类型：1普通组队 2商业活动',
    `location_name` VARCHAR(200) NOT NULL COMMENT '活动地点',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `start_time` DATETIME NOT NULL COMMENT '活动开始时间',
    `end_time` DATETIME NOT NULL COMMENT '活动结束时间',
    `max_members` INT DEFAULT 0 COMMENT '最大人数（0为不限）',
    `current_members` INT DEFAULT 0 COMMENT '当前报名人数',
    `fee_type` TINYINT DEFAULT 1 COMMENT '费用类型：1免费 2AA制 3固定费用',
    `fee_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '费用金额',
    `requirement` VARCHAR(500) DEFAULT NULL COMMENT '参与要求',
    `is_top` TINYINT DEFAULT 0 COMMENT '是否置顶',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0取消 1报名中 2已满 3进行中 4已结束',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_start_time` (`start_time`),
    KEY `idx_status` (`status`),
    KEY `idx_location` (`latitude`, `longitude`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

-- -----------------------------------------------------------
-- 11. 活动报名表
-- -----------------------------------------------------------
CREATE TABLE `t_activity_member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `activity_id` BIGINT NOT NULL COMMENT '活动ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role` TINYINT DEFAULT 2 COMMENT '角色：1发起人 2参与者',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0取消 1已报名 2已确认',
    `joined_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_activity_user` (`activity_id`, `user_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动报名表';

-- -----------------------------------------------------------
-- 12. 会员订单表
-- -----------------------------------------------------------
CREATE TABLE `t_member_order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `order_no` VARCHAR(64) NOT NULL COMMENT '订单编号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `member_type` TINYINT NOT NULL COMMENT '会员类型：1月度 2季度 3年度',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
    `pay_type` TINYINT DEFAULT NULL COMMENT '支付方式：1微信 2支付宝',
    `trade_no` VARCHAR(128) DEFAULT NULL COMMENT '第三方交易号',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0待支付 1已支付 2已退款 3已过期',
    `paid_at` DATETIME DEFAULT NULL COMMENT '支付时间',
    `expire_at` DATETIME DEFAULT NULL COMMENT '到期时间',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员订单表';

-- -----------------------------------------------------------
-- 13. 虚拟礼物定义表
-- -----------------------------------------------------------
CREATE TABLE `t_gift` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '礼物ID',
    `name` VARCHAR(50) NOT NULL COMMENT '礼物名称',
    `icon` VARCHAR(500) NOT NULL COMMENT '礼物图标URL',
    `price` INT NOT NULL COMMENT '价格（金币）',
    `animation_url` VARCHAR(500) DEFAULT NULL COMMENT '动画效果URL',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0下架 1上架',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='虚拟礼物定义表';

-- -----------------------------------------------------------
-- 14. 礼物打赏记录表
-- -----------------------------------------------------------
CREATE TABLE `t_gift_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `sender_id` BIGINT NOT NULL COMMENT '送礼者ID',
    `receiver_id` BIGINT NOT NULL COMMENT '收礼者ID',
    `gift_id` BIGINT NOT NULL COMMENT '礼物ID',
    `gift_count` INT DEFAULT 1 COMMENT '数量',
    `total_coin` BIGINT NOT NULL COMMENT '总金币数',
    `target_type` TINYINT DEFAULT 1 COMMENT '场景：1动态打赏 2私信打赏 3直播打赏',
    `target_id` BIGINT DEFAULT NULL COMMENT '目标ID（动态ID等）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '打赏时间',
    PRIMARY KEY (`id`),
    KEY `idx_sender_id` (`sender_id`),
    KEY `idx_receiver_id` (`receiver_id`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='礼物打赏记录表';

-- -----------------------------------------------------------
-- 15. 充值/支付流水表
-- -----------------------------------------------------------
CREATE TABLE `t_payment_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `order_no` VARCHAR(64) NOT NULL COMMENT '订单编号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `biz_type` TINYINT NOT NULL COMMENT '业务类型：1会员购买 2金币充值 3活动报名 4动态置顶 5商户入驻',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '金额',
    `pay_type` TINYINT NOT NULL COMMENT '支付方式：1微信 2支付宝',
    `trade_no` VARCHAR(128) DEFAULT NULL COMMENT '第三方交易号',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0待支付 1成功 2失败 3退款',
    `callback_time` DATETIME DEFAULT NULL COMMENT '回调时间',
    `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_biz_type` (`biz_type`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付流水表';

-- -----------------------------------------------------------
-- 16. 商户表
-- -----------------------------------------------------------
CREATE TABLE `t_merchant` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商户ID',
    `user_id` BIGINT NOT NULL COMMENT '关联用户ID',
    `merchant_name` VARCHAR(100) NOT NULL COMMENT '商户名称',
    `merchant_type` TINYINT NOT NULL COMMENT '类型：1营地 2装备商 3向导',
    `logo` VARCHAR(500) DEFAULT NULL COMMENT 'LOGO',
    `description` TEXT COMMENT '商户介绍',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `address` VARCHAR(200) DEFAULT NULL COMMENT '地址',
    `longitude` DECIMAL(10,6) DEFAULT NULL COMMENT '经度',
    `latitude` DECIMAL(10,6) DEFAULT NULL COMMENT '纬度',
    `license_image` VARCHAR(500) DEFAULT NULL COMMENT '营业执照图片',
    `annual_fee_paid` TINYINT DEFAULT 0 COMMENT '年费是否已支付',
    `fee_expire_time` DATETIME DEFAULT NULL COMMENT '年费到期时间',
    `rating` DECIMAL(2,1) DEFAULT 5.0 COMMENT '评分',
    `status` TINYINT DEFAULT 0 COMMENT '状态：0待审核 1正常 2冻结',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_merchant_type` (`merchant_type`),
    KEY `idx_location` (`latitude`, `longitude`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户表';

-- -----------------------------------------------------------
-- 17. 邀请记录表
-- -----------------------------------------------------------
CREATE TABLE `t_invite_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `inviter_id` BIGINT NOT NULL COMMENT '邀请人ID',
    `invitee_id` BIGINT NOT NULL COMMENT '被邀请人ID',
    `reward_type` TINYINT DEFAULT 1 COMMENT '奖励类型：1会员体验天数 2金币',
    `reward_value` INT DEFAULT 0 COMMENT '奖励值',
    `status` TINYINT DEFAULT 1 COMMENT '状态：1已注册 2已激活（完善资料）',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '邀请时间',
    PRIMARY KEY (`id`),
    KEY `idx_inviter_id` (`inviter_id`),
    UNIQUE KEY `uk_invitee_id` (`invitee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邀请记录表';

-- -----------------------------------------------------------
-- 18. 敏感词库表
-- -----------------------------------------------------------
CREATE TABLE `t_sensitive_word` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `word` VARCHAR(100) NOT NULL COMMENT '敏感词',
    `category` TINYINT DEFAULT 1 COMMENT '分类：1政治 2色情 3广告 4辱骂 5其他',
    `replace_text` VARCHAR(100) DEFAULT '***' COMMENT '替换文本',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0禁用 1启用',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_word` (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='敏感词库表';

-- -----------------------------------------------------------
-- 19. 举报表
-- -----------------------------------------------------------
CREATE TABLE `t_report` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `reporter_id` BIGINT NOT NULL COMMENT '举报人ID',
    `target_id` BIGINT NOT NULL COMMENT '被举报目标ID',
    `target_type` TINYINT NOT NULL COMMENT '目标类型：1用户 2动态 3评论 4活动',
    `reason` TINYINT NOT NULL COMMENT '举报原因：1色情 2广告 3骚扰 4虚假信息 5其他',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '详细描述',
    `images` VARCHAR(1000) DEFAULT NULL COMMENT '截图证据',
    `handle_status` TINYINT DEFAULT 0 COMMENT '处理状态：0待处理 1已处理 2已驳回',
    `handle_result` VARCHAR(200) DEFAULT NULL COMMENT '处理结果',
    `handler_id` BIGINT DEFAULT NULL COMMENT '处理人',
    `handled_at` DATETIME DEFAULT NULL COMMENT '处理时间',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '举报时间',
    PRIMARY KEY (`id`),
    KEY `idx_handle_status` (`handle_status`),
    KEY `idx_reporter_id` (`reporter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='举报表';

-- -----------------------------------------------------------
-- 20. 系统配置表
-- -----------------------------------------------------------
CREATE TABLE `t_system_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `config_key` VARCHAR(100) NOT NULL COMMENT '配置键',
    `config_value` TEXT NOT NULL COMMENT '配置值',
    `config_desc` VARCHAR(200) DEFAULT NULL COMMENT '配置说明',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';
```

---

## 六、Redis 缓存设计

### 6.1 Redis 配置

```yaml
# application.yml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    password: your_password
    database: 0
    lettuce:
      pool:
        max-active: 50
        max-idle: 20
        min-idle: 5
        max-wait: 3000ms
```

### 6.2 缓存 Key 设计规范

```
命名规则：lyd:{业务模块}:{细分功能}:{唯一标识}
```

| Key | 类型 | 过期时间 | 用途 |
|-----|------|----------|------|
| `lyd:user:info:{userId}` | Hash | 30min | 用户基本信息缓存 |
| `lyd:user:token:{token}` | String | 7天 | 用户登录Token |
| `lyd:user:online:{userId}` | String | 5min | 用户在线状态 |
| `lyd:user:location:{userId}` | Geo | 30min | 用户实时位置（GEO） |
| `lyd:sms:code:{phone}` | String | 5min | 短信验证码 |
| `lyd:sms:limit:{phone}` | String | 24h | 短信发送次数限制 |
| `lyd:post:like:count:{postId}` | String | 持久 | 动态点赞计数 |
| `lyd:post:like:users:{postId}` | Set | 持久 | 动态点赞用户集合 |
| `lyd:post:hot:{city}` | ZSet | 1h | 同城热门动态排行 |
| `lyd:chat:unread:{userId}` | Hash | 持久 | 用户未读消息数 |
| `lyd:match:daily:{userId}` | String | 到24点 | 每日匹配刷新次数 |
| `lyd:chat:daily:{userId}` | Set | 到24点 | 每日私信对象（限流） |
| `lyd:member:info:{userId}` | Hash | 1h | 会员信息缓存 |
| `lyd:activity:hot` | ZSet | 30min | 热门活动排行 |
| `lyd:invite:code:{code}` | String | 持久 | 邀请码→用户ID映射 |
| `lyd:sensitive:version` | String | 持久 | 敏感词库版本号 |
| `lyd:rate:limit:{api}:{userId}` | String | 1s-1min | 接口限流 |

### 6.3 关键缓存策略

```
1. 用户位置匹配 — Redis GEO
   GEOADD lyd:user:location:{city} longitude latitude userId
   GEORADIUS lyd:user:location:{city} lon lat 10 km COUNT 20 ASC

2. 热门动态排行 — ZSet（score=综合热度分）
   热度分 = 点赞数*2 + 评论数*3 + 分享数*5 + 时间衰减因子

3. 消息已读状态 — 基于 BitMap 或 Hash
   降低数据库写入压力，定时异步同步到MySQL

4. 限流策略 — 令牌桶/滑动窗口
   免费用户每日私信3人：SADD lyd:chat:daily:{userId} targetId
   每日匹配5次：INCR lyd:match:daily:{userId}（EXPIREAT到当日24点）
```

---

## 七、部署方案

### 7.1 服务器配置建议

| 阶段 | 配置 | 预估成本/月 |
|------|------|-------------|
| MVP（0-1万用户） | 2核4G×1 + RDS基础版 + 1GB Redis | ¥500-800 |
| 成长期（1-10万用户） | 4核8G×2 + RDS高可用 + 4GB Redis + OSS | ¥2,000-3,000 |
| 规模期（10万+用户） | 8核16G×4 + RDS集群 + Redis集群 + ES + CDN | ¥8,000-15,000 |

### 7.2 Docker Compose 部署

```yaml
# doc/deploy/docker-compose.yml
version: '3.8'
services:
  # 应用服务
  luyingdazi-api:
    image: luyingdazi/api:latest
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - MYSQL_HOST=mysql
      - REDIS_HOST=redis
    depends_on:
      - mysql
      - redis
    restart: always

  # MySQL
  mysql:
    image: mysql:8.0
    ports:
      - "3306:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=your_password
      - MYSQL_DATABASE=luyingdazi
    volumes:
      - mysql_data:/var/lib/mysql
      - ./sql:/docker-entrypoint-initdb.d
    restart: always

  # Redis
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    command: redis-server --requirepass your_password --maxmemory 512mb --maxmemory-policy allkeys-lru
    volumes:
      - redis_data:/data
    restart: always

  # Nginx
  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
      - ./ssl:/etc/nginx/ssl
    depends_on:
      - luyingdazi-api
    restart: always

  # Elasticsearch（可选，用户量大后启用）
  # elasticsearch:
  #   image: elasticsearch:8.10.0
  #   ports:
  #     - "9200:9200"
  #   environment:
  #     - discovery.type=single-node
  #     - "ES_JAVA_OPTS=-Xms512m -Xmx512m"

volumes:
  mysql_data:
  redis_data:
```

### 7.3 Nginx 配置

```nginx
# doc/deploy/nginx.conf
server {
    listen 443 ssl http2;
    server_name api.luyingdazi.com;

    ssl_certificate /etc/nginx/ssl/fullchain.pem;
    ssl_certificate_key /etc/nginx/ssl/privkey.pem;

    # API 转发
    location /api/ {
        proxy_pass http://luyingdazi-api:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

    # WebSocket（即时通讯）
    location /ws/ {
        proxy_pass http://luyingdazi-api:8080/ws/;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_read_timeout 3600s;
    }

    # 静态资源 / 管理后台
    location / {
        root /usr/share/nginx/html;
        try_files $uri $uri/ /index.html;
    }
}
```

---

## 八、第三方服务接入清单

| 服务 | 提供商 | 用途 | 费用 |
|------|--------|------|------|
| 短信验证码 | 阿里云SMS | 注册/登录 | ¥0.045/条 |
| 人脸实名认证 | 阿里云实人认证 | 身份核验 | ¥0.85/次 |
| 内容安全 | 阿里云内容安全 | 文本+图片审核 | ¥0.0025/条 |
| 对象存储 | 阿里云OSS | 图片/视频存储 | 按量付费 |
| CDN | 阿里云CDN | 静态资源加速 | ¥0.24/GB |
| 微信支付 | 微信商户平台 | 在线支付 | 0.6%费率 |
| 支付宝支付 | 支付宝开放平台 | 在线支付 | 0.6%费率 |
| 地图服务 | 高德开放平台 | LBS定位+地理编码 | 免费额度充足 |
| 消息推送 | 极光推送/UniPush | APP推送通知 | 免费+按量 |
| 微信登录 | 微信开放平台 | 一键登录 | 免费 |
| SSL证书 | Let's Encrypt | HTTPS | 免费 |

---

## 九、开发排期建议

### MVP 阶段（8-10周）

| 周次 | 任务 | 交付物 |
|------|------|--------|
| 第1-2周 | 项目初始化+用户模块 | 注册登录、个人资料、微信登录 |
| 第3-4周 | 社交模块核心 | 动态发布、评论点赞、关注粉丝 |
| 第5-6周 | IM即时通讯 | WebSocket私信、消息列表 |
| 第7周 | 同城匹配+活动 | LBS推荐、活动发起/报名 |
| 第8周 | 支付+会员 | 微信/支付宝支付、会员购买 |
| 第9周 | 内容安全+管理后台 | 敏感词过滤、基础后台 |
| 第10周 | 联调测试+部署 | 全链路测试、上线部署 |

### 完善阶段（4-6周）

- 虚拟礼物系统
- 商户入驻模块
- 邀请裂变系统
- 数据统计仪表盘
- 性能优化+安全加固

---

## 十、合规与资质要求

| 资质 | 用途 | 获取方式 |
|------|------|----------|
| ICP备案 | 网站/App上线必须 | 通过云服务商提交 |
| APP上架审核 | 应用商店发布 | 准备隐私协议+功能说明 |
| 小程序类目 | 微信小程序上线 | 选择「社交」或「生活服务」类目 |
| 支付商户号 | 在线收款 | 企业营业执照申请 |
| 增值电信业务许可证（EDI） | 虚拟商品交易 | 用户量大后办理 |
| 网络文化经营许可证 | 虚拟礼物/打赏 | 涉及虚拟货币需办理 |

---

## 十一、风险与注意事项

1. **社交安全**：必须接入实名认证，防止未成年人使用；建立完善的举报-封禁机制
2. **内容合规**：所有 UGC 内容必须经过机审+人审，避免违规内容传播
3. **支付安全**：金币充值/提现需做好风控，防止刷单和洗钱
4. **数据安全**：用户隐私数据加密存储，遵循《个人信息保护法》
5. **防薅羊毛**：邀请裂变系统设置风控规则，防止批量注册骗取奖励
6. **性能规划**：社交平台读多写少，做好 Redis 缓存层设计，避免数据库瓶颈

---

## 十二、快速启动

```bash
# 1. 克隆项目
git clone https://github.com/yourname/luyingdazi.git
cd luyingdazi

# 2. 初始化数据库
mysql -u root -p < doc/sql/init_schema.sql
mysql -u root -p luyingdazi < doc/sql/init_data.sql

# 3. 启动后端
cd luyingdazi-server
mvn clean install -DskipTests
mvn spring-boot:run -pl luyingdazi-api -Dspring.profiles.active=dev

# 4. 启动前端
cd luyingdazi-app
npm install
npm run dev:mp-weixin  # 微信小程序
# 或
npm run dev:h5         # H5端

# 5. 启动管理后台
cd luyingdazi-admin
npm install
npm run dev
```

---

## 十三、总结

本项目是一个完整的户外露营垂直社交平台解决方案，涵盖：

- ✅ **用户体系**：注册登录 + 人脸实名 + 粉丝关注 + 访客记录
- ✅ **社交功能**：图文动态 + 评论点赞 + 私信聊天 + 智能匹配
- ✅ **盈利模型**：会员订阅 + 虚拟礼物 + 活动抽佣 + 动态置顶 + 商户入驻
- ✅ **配套工具**：内容过滤 + 邀请裂变 + 支付回调 + 权限控制
- ✅ **技术方案**：Spring Boot + UniApp + MySQL + Redis + WebSocket
- ✅ **部署方案**：Docker + Nginx + 阿里云全家桶

建议先完成 MVP 版本验证产品逻辑和用户需求，再逐步迭代完善功能。

---

*文档版本：v1.0 | 更新时间：2026-06-19 | 作者：野趣搭子团队*

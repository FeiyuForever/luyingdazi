package com.luyingdazi.common.constant;

/**
 * Redis Key 常量
 * 命名规则：lyd:{模块}:{功能}:{标识}
 *
 * @author luyingdazi
 */
public final class RedisKeyConstant {

    private RedisKeyConstant() {}

    /** 前缀 */
    private static final String PREFIX = "lyd:";

    // ========== 用户模块 ==========

    /** 用户登录Token → userId, TTL: 7天 */
    public static final String USER_TOKEN = PREFIX + "user:token:";

    /** 用户信息缓存, TTL: 30分钟 */
    public static final String USER_INFO = PREFIX + "user:info:";

    /** 用户在线状态, TTL: 5分钟（心跳续期） */
    public static final String USER_ONLINE = PREFIX + "ws:online:";

    // ========== 地理位置模块 ==========

    /** 用户地理位置（GEO类型），按城市分 Key */
    public static final String GEO_USER = PREFIX + "geo:user";

    // ========== 社交模块 ==========

    /** 动态点赞用户集合（Set类型） */
    public static final String POST_LIKE_USERS = PREFIX + "post:like:";

    /** 同城热门动态（ZSet类型）, TTL: 1小时 */
    public static final String POST_HOT = PREFIX + "post:hot:";

    // ========== 聊天模块 ==========

    /** 用户未读消息数（Hash: sessionId → count） */
    public static final String CHAT_UNREAD = PREFIX + "chat:unread:";

    // ========== 限流模块 ==========

    /** 每日私信对象限制（Set类型）, TTL: 到当日24点 */
    public static final String LIMIT_CHAT_DAILY = PREFIX + "limit:chat:";

    /** 每日匹配刷新次数, TTL: 到当日24点 */
    public static final String LIMIT_MATCH_DAILY = PREFIX + "limit:match:";

    /** 接口限流 */
    public static final String RATE_LIMIT = PREFIX + "rate:limit:";
}

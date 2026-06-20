package com.luyingdazi.common.constant;

/**
 * 应用常量
 *
 * @author luyingdazi
 */
public final class AppConstant {

    private AppConstant() {}

    /** Token 请求头名称 */
    public static final String TOKEN_HEADER = "Authorization";

    /** Token 前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** Token 有效期（秒）：7天 */
    public static final long TOKEN_EXPIRE_SECONDS = 7 * 24 * 3600;

    /** 免费用户每日私信人数上限 */
    public static final int FREE_DAILY_CHAT_LIMIT = 3;

    /** 免费用户每日匹配刷新上限 */
    public static final int FREE_DAILY_MATCH_LIMIT = 5;

    /** 附近的人默认搜索半径（千米） */
    public static final double DEFAULT_NEARBY_RADIUS_KM = 10.0;

    /** 附近的人默认返回数量 */
    public static final int DEFAULT_NEARBY_COUNT = 20;

    /** 动态图片最大数量 */
    public static final int POST_MAX_IMAGES = 9;

    /** 用户昵称最大长度 */
    public static final int NICKNAME_MAX_LENGTH = 20;

    /** 个人简介最大长度 */
    public static final int BIO_MAX_LENGTH = 200;
}

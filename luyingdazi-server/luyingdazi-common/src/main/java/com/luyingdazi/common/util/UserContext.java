package com.luyingdazi.common.util;

/**
 * 用户上下文（基于 ThreadLocal）
 * 用于在整个请求链路中传递当前登录用户信息
 *
 * @author luyingdazi
 */
public final class UserContext {

    private UserContext() {}

    private static final ThreadLocal<Long> USER_ID_HOLDER = new ThreadLocal<>();

    /**
     * 设置当前用户ID（在拦截器中调用）
     */
    public static void setUserId(Long userId) {
        USER_ID_HOLDER.set(userId);
    }

    /**
     * 获取当前用户ID
     */
    public static Long getUserId() {
        return USER_ID_HOLDER.get();
    }

    /**
     * 清除（在拦截器 afterCompletion 中调用，防止内存泄漏）
     */
    public static void clear() {
        USER_ID_HOLDER.remove();
    }
}

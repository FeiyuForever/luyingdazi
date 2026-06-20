package com.luyingdazi.service.content;

/**
 * 内容安全服务接口
 * 负责文本/图片的违规检测（本地DFA + 微信内容安全API）
 *
 * @author luyingdazi
 */
public interface ContentSecurityService {

    /**
     * 文本内容检测
     * 1. 本地 DFA 敏感词匹配
     * 2. 微信 msg_sec_check API
     *
     * @param text   待检测文本
     * @param userId 用户的openid（微信API需要）
     * @return true=安全, false=违规
     */
    boolean checkText(String text, String userId);

    /**
     * 图片内容检测
     * 调用微信 img_sec_check API
     *
     * @param imageUrl 图片URL
     * @param userId   用户的openid
     * @return true=安全, false=违规
     */
    boolean checkImage(String imageUrl, String userId);

    /**
     * 文本敏感词替换（替换为***）
     *
     * @param text 原始文本
     * @return 过滤后的文本
     */
    String filterText(String text);
}

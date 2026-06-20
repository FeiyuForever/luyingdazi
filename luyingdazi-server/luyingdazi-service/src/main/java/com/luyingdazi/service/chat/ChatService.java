package com.luyingdazi.service.chat;

import com.luyingdazi.common.result.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 聊天服务接口
 *
 * @author luyingdazi
 */
public interface ChatService {

    /**
     * 发送消息
     *
     * @param senderId   发送者ID
     * @param receiverId 接收者ID
     * @param msgType    消息类型（1文本 2图片 3位置）
     * @param content    消息内容
     * @return 消息ID
     */
    Long sendMessage(Long senderId, Long receiverId, Integer msgType, String content);

    /**
     * 获取会话列表
     *
     * @param userId 当前用户ID
     * @return 会话列表（含最后消息+未读数+对方用户信息）
     */
    List<Map<String, Object>> getSessionList(Long userId);

    /**
     * 获取聊天记录
     *
     * @param userId     当前用户ID
     * @param sessionId  会话ID
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @return 消息列表（时间正序）
     */
    PageResult<Map<String, Object>> getMessages(Long userId, Long sessionId,
                                                 int pageNum, int pageSize);

    /**
     * 标记消息已读
     *
     * @param userId    当前用户ID
     * @param sessionId 会话ID
     */
    void markRead(Long userId, Long sessionId);

    /**
     * 检查今日私信限制（免费用户每天3人）
     *
     * @param userId     用户ID
     * @param targetId   目标用户ID
     * @return true=可以发送, false=达到限制
     */
    boolean checkChatLimit(Long userId, Long targetId);
}

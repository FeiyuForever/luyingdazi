package com.luyingdazi.service.chat.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luyingdazi.common.constant.AppConstant;
import com.luyingdazi.common.constant.RedisKeyConstant;
import com.luyingdazi.common.exception.BizException;
import com.luyingdazi.common.result.PageResult;
import com.luyingdazi.common.result.ResultCode;
import com.luyingdazi.mapper.ChatMessageMapper;
import com.luyingdazi.mapper.ChatSessionMapper;
import com.luyingdazi.mapper.UserMapper;
import com.luyingdazi.model.entity.ChatMessage;
import com.luyingdazi.model.entity.ChatSession;
import com.luyingdazi.model.entity.User;
import com.luyingdazi.service.chat.ChatService;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 聊天服务实现
 *
 * @author luyingdazi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatSessionMapper chatSessionMapper;
    private final ChatMessageMapper chatMessageMapper;
    private final UserMapper userMapper;
    private final StringRedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long sendMessage(Long senderId, Long receiverId, Integer msgType, String content) {
        // 1. 检查每日私信限制
        if (!checkChatLimit(senderId, receiverId)) {
            throw new BizException(ResultCode.CHAT_LIMIT_REACHED);
        }

        // 2. 获取或创建会话
        ChatSession session = getOrCreateSession(senderId, receiverId);

        // 3. 保存消息
        ChatMessage message = new ChatMessage();
        message.setSessionId(session.getId());
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setMsgType(msgType);
        message.setContent(content);
        message.setIsRead(0);
        message.setStatus(1);
        chatMessageMapper.insert(message);

        // 4. 更新会话最后消息
        String msgSummary = StrUtil.sub(content, 0, 50);
        if (msgType == 2) msgSummary = "[图片]";
        if (msgType == 3) msgSummary = "[位置]";

        chatSessionMapper.update(null, new LambdaUpdateWrapper<ChatSession>()
                .eq(ChatSession::getId, session.getId())
                .set(ChatSession::getLastMsg, msgSummary)
                .set(ChatSession::getLastMsgTime, LocalDateTime.now()));

        // 5. 增加未读数
        boolean senderIsA = senderId.equals(session.getUserAId());
        if (senderIsA) {
            chatSessionMapper.update(null, new LambdaUpdateWrapper<ChatSession>()
                    .eq(ChatSession::getId, session.getId())
                    .setSql("b_unread = b_unread + 1"));
        } else {
            chatSessionMapper.update(null, new LambdaUpdateWrapper<ChatSession>()
                    .eq(ChatSession::getId, session.getId())
                    .setSql("a_unread = a_unread + 1"));
        }

        // 6. 记录今日私信对象
        recordChatTarget(senderId, receiverId);

        return message.getId();
    }

    @Override
    public List<Map<String, Object>> getSessionList(Long userId) {
        // 查询该用户参与的所有会话
        LambdaQueryWrapper<ChatSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatSession::getUserAId, userId)
                .or()
                .eq(ChatSession::getUserBId, userId);
        wrapper.orderByDesc(ChatSession::getLastMsgTime);

        List<ChatSession> sessions = chatSessionMapper.selectList(wrapper);

        // 组装结果
        return sessions.stream().map(session -> {
            Map<String, Object> map = new HashMap<>();
            map.put("sessionId", session.getId());
            map.put("lastMsg", session.getLastMsg());
            map.put("lastMsgTime", session.getLastMsgTime());

            // 确定对方用户
            Long targetUserId = session.getUserAId().equals(userId)
                    ? session.getUserBId() : session.getUserAId();
            int unread = session.getUserAId().equals(userId)
                    ? session.getAUnread() : session.getBUnread();

            map.put("unread", unread);

            // 查对方信息
            User targetUser = userMapper.selectById(targetUserId);
            if (targetUser != null) {
                map.put("targetUserId", targetUser.getId());
                map.put("targetNickname", targetUser.getNickname());
                map.put("targetAvatar", targetUser.getAvatar());
            }
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public PageResult<Map<String, Object>> getMessages(Long userId, Long sessionId,
                                                        int pageNum, int pageSize) {
        LambdaQueryWrapper<ChatMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatMessage::getSessionId, sessionId)
                .eq(ChatMessage::getStatus, 1)
                .orderByDesc(ChatMessage::getCreatedAt);

        Page<ChatMessage> page = chatMessageMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        List<Map<String, Object>> records = page.getRecords().stream().map(msg -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", msg.getId());
            map.put("senderId", msg.getSenderId());
            map.put("receiverId", msg.getReceiverId());
            map.put("msgType", msg.getMsgType());
            map.put("content", msg.getContent());
            map.put("isRead", msg.getIsRead());
            map.put("createdAt", msg.getCreatedAt());
            map.put("isMine", msg.getSenderId().equals(userId));
            return map;
        }).collect(Collectors.toList());

        // 反转为时间正序展示
        Collections.reverse(records);
        return PageResult.of(records, page.getTotal(), pageNum, pageSize);
    }

    @Override
    public void markRead(Long userId, Long sessionId) {
        ChatSession session = chatSessionMapper.selectById(sessionId);
        if (session == null) return;

        // 清零当前用户的未读数
        if (session.getUserAId().equals(userId)) {
            chatSessionMapper.update(null, new LambdaUpdateWrapper<ChatSession>()
                    .eq(ChatSession::getId, sessionId)
                    .set(ChatSession::getAUnread, 0));
        } else {
            chatSessionMapper.update(null, new LambdaUpdateWrapper<ChatSession>()
                    .eq(ChatSession::getId, sessionId)
                    .set(ChatSession::getBUnread, 0));
        }

        // 标记消息已读
        chatMessageMapper.update(null, new LambdaUpdateWrapper<ChatMessage>()
                .eq(ChatMessage::getSessionId, sessionId)
                .eq(ChatMessage::getReceiverId, userId)
                .eq(ChatMessage::getIsRead, 0)
                .set(ChatMessage::getIsRead, 1));
    }

    @Override
    public boolean checkChatLimit(Long userId, Long targetId) {
        String key = RedisKeyConstant.LIMIT_CHAT_DAILY + userId;

        // 如果已经跟这个人聊过，不算新对象
        Boolean exists = redisTemplate.opsForSet().isMember(key, String.valueOf(targetId));
        if (Boolean.TRUE.equals(exists)) {
            return true;
        }

        // 检查今日新对象数是否达到上限
        Long size = redisTemplate.opsForSet().size(key);
        // TODO: 检查是否会员，会员不限
        return size == null || size < AppConstant.FREE_DAILY_CHAT_LIMIT;
    }

    // ==================== 私有方法 ====================

    private ChatSession getOrCreateSession(Long userA, Long userB) {
        // 确保 userAId < userBId
        Long smallId = Math.min(userA, userB);
        Long bigId = Math.max(userA, userB);

        ChatSession session = chatSessionMapper.selectOne(
                new LambdaQueryWrapper<ChatSession>()
                        .eq(ChatSession::getUserAId, smallId)
                        .eq(ChatSession::getUserBId, bigId));

        if (session == null) {
            session = new ChatSession();
            session.setUserAId(smallId);
            session.setUserBId(bigId);
            session.setAUnread(0);
            session.setBUnread(0);
            chatSessionMapper.insert(session);
        }
        return session;
    }

    private void recordChatTarget(Long userId, Long targetId) {
        String key = RedisKeyConstant.LIMIT_CHAT_DAILY + userId;
        redisTemplate.opsForSet().add(key, String.valueOf(targetId));
        // 到今天 24 点过期
        long seconds = LocalDate.now().plusDays(1).atStartOfDay()
                .toEpochSecond(ZoneOffset.ofHours(8)) - System.currentTimeMillis() / 1000;
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }
}

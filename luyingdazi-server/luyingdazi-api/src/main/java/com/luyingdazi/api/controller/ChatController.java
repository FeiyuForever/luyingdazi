package com.luyingdazi.api.controller;

import com.luyingdazi.common.result.PageResult;
import com.luyingdazi.common.result.Result;
import com.luyingdazi.common.util.UserContext;
import com.luyingdazi.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 聊天接口（HTTP部分，WebSocket在单独的Handler中）
 *
 * @author luyingdazi
 */
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 获取会话列表
     */
    @GetMapping("/sessions")
    public Result<List<Map<String, Object>>> getSessions() {
        return Result.success(chatService.getSessionList(UserContext.getUserId()));
    }

    /**
     * 获取聊天记录
     */
    @GetMapping("/messages/{sessionId}")
    public Result<PageResult<Map<String, Object>>> getMessages(
            @PathVariable Long sessionId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize) {
        return Result.success(chatService.getMessages(UserContext.getUserId(), sessionId, pageNum, pageSize));
    }

    /**
     * 标记已读
     */
    @PostMapping("/read/{sessionId}")
    public Result<Void> markRead(@PathVariable Long sessionId) {
        chatService.markRead(UserContext.getUserId(), sessionId);
        return Result.success();
    }

    /**
     * 发送消息（HTTP方式，WebSocket不可用时的降级方案）
     */
    @PostMapping("/send")
    public Result<Long> sendMessage(@RequestBody Map<String, Object> params) {
        Long receiverId = Long.valueOf(params.get("receiverId").toString());
        Integer msgType = Integer.valueOf(params.getOrDefault("msgType", 1).toString());
        String content = params.get("content").toString();
        return Result.success(chatService.sendMessage(UserContext.getUserId(), receiverId, msgType, content));
    }
}

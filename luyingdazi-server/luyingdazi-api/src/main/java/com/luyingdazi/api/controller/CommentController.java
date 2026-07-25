package com.luyingdazi.api.controller;

import com.luyingdazi.api.service.OssUrlService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.luyingdazi.common.exception.BizException;
import com.luyingdazi.common.result.Result;
import com.luyingdazi.common.result.ResultCode;
import com.luyingdazi.common.util.UserContext;
import com.luyingdazi.mapper.CommentMapper;
import com.luyingdazi.mapper.PostMapper;
import com.luyingdazi.mapper.UserMapper;
import com.luyingdazi.model.entity.Comment;
import com.luyingdazi.model.entity.Post;
import com.luyingdazi.model.entity.User;
import com.luyingdazi.service.content.ContentSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 评论接口
 *
 * @author luyingdazi
 */
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentMapper commentMapper;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final ContentSecurityService contentSecurityService;
    private final OssUrlService ossUrlService;

    /**
     * 获取动态的评论列表
     */
    @GetMapping("/list/{postId}")
    public Result<List<Map<String, Object>>> getComments(@PathVariable Long postId) {
        List<Comment> comments = commentMapper.selectList(
                new LambdaQueryWrapper<Comment>()
                        .eq(Comment::getPostId, postId)
                        .eq(Comment::getStatus, 1)
                        .orderByAsc(Comment::getCreatedAt));

        if (comments.isEmpty()) {
            return Result.success(Collections.emptyList());
        }

        // 批量查用户信息
        Set<Long> userIds = comments.stream().map(Comment::getUserId).collect(Collectors.toSet());
        Map<Long, User> userMap = userMapper.selectBatchIds(userIds).stream()
                .collect(Collectors.toMap(User::getId, u -> u));

        // 组装结果
        List<Map<String, Object>> result = comments.stream().map(c -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getId());
            map.put("postId", c.getPostId());
            map.put("userId", c.getUserId());
            map.put("parentId", c.getParentId());
            map.put("replyUserId", c.getReplyUserId());
            map.put("content", c.getContent());
            map.put("createdAt", c.getCreatedAt());

            User u = userMap.get(c.getUserId());
            if (u != null) {
                map.put("nickname", u.getNickname());
                map.put("avatar", ossUrlService.toAccessibleUrl(u.getAvatar()));
            }
            return map;
        }).collect(Collectors.toList());

        return Result.success(result);
    }

    /**
     * 发表评论
     */
    @PostMapping("/add")
    public Result<Long> addComment(@RequestBody Map<String, Object> params) {
        Long userId = UserContext.getUserId();
        Long postId = Long.valueOf(params.get("postId").toString());
        String content = params.get("content").toString();
        Long parentId = params.containsKey("parentId") ? Long.valueOf(params.get("parentId").toString()) : 0L;
        Long replyUserId = params.containsKey("replyUserId") ? Long.valueOf(params.get("replyUserId").toString()) : null;

        // 内容安全检测
        if (!contentSecurityService.checkText(content, String.valueOf(userId))) {
            throw new BizException(ResultCode.CONTENT_ILLEGAL);
        }

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setParentId(parentId);
        comment.setReplyUserId(replyUserId);
        comment.setContent(content);
        comment.setStatus(1);
        comment.setCreatedAt(LocalDateTime.now());
        commentMapper.insert(comment);

        // 更新动态评论数
        postMapper.update(null, new LambdaUpdateWrapper<Post>()
                .eq(Post::getId, postId)
                .setSql("comment_count = comment_count + 1"));

        return Result.success(comment.getId());
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/{commentId}")
    public Result<Void> deleteComment(@PathVariable Long commentId) {
        Long userId = UserContext.getUserId();
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null || !comment.getUserId().equals(userId)) {
            throw new BizException(ResultCode.PERMISSION_DENIED);
        }
        commentMapper.update(null, new LambdaUpdateWrapper<Comment>()
                .eq(Comment::getId, commentId)
                .set(Comment::getStatus, 0));

        // 更新动态评论数
        postMapper.update(null, new LambdaUpdateWrapper<Post>()
                .eq(Post::getId, comment.getPostId())
                .setSql("comment_count = GREATEST(comment_count - 1, 0)"));

        return Result.success();
    }
}

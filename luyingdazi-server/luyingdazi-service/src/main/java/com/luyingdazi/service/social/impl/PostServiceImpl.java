package com.luyingdazi.service.social.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luyingdazi.common.constant.RedisKeyConstant;
import com.luyingdazi.common.exception.BizException;
import com.luyingdazi.common.result.PageResult;
import com.luyingdazi.common.result.ResultCode;
import com.luyingdazi.mapper.PostMapper;
import com.luyingdazi.model.entity.Post;
import com.luyingdazi.service.content.ContentSecurityService;
import com.luyingdazi.service.social.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动态服务实现
 *
 * @author luyingdazi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final StringRedisTemplate redisTemplate;
    private final ContentSecurityService contentSecurityService;

    @Override
    public Long publishPost(Long userId, Post post) {
        // 1. 内容安全检测
        if (!contentSecurityService.checkText(post.getContent(), String.valueOf(userId))) {
            throw new BizException(ResultCode.CONTENT_ILLEGAL);
        }

        // 2. 保存动态
        post.setUserId(userId);
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setIsTop(0);
        post.setStatus(1);
        postMapper.insert(post);

        log.info("用户{}发布动态: postId={}", userId, post.getId());
        return post.getId();
    }

    @Override
    public Post getPostDetail(Long postId, Long currentUserId) {
        Post post = postMapper.selectById(postId);
        if (post == null || post.getStatus() == 0) {
            throw new BizException(ResultCode.POST_NOT_FOUND);
        }
        return post;
    }

    @Override
    public PageResult<Post> getPostFeed(int pageNum, int pageSize) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1)
                .orderByDesc(Post::getIsTop)    // 置顶优先
                .orderByDesc(Post::getCreatedAt); // 时间倒序

        Page<Post> page = postMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return PageResult.of(page.getRecords(), page.getTotal(), pageNum, pageSize);
    }

    @Override
    public PageResult<Post> getUserPosts(Long userId, int pageNum, int pageSize) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getUserId, userId)
                .eq(Post::getStatus, 1)
                .orderByDesc(Post::getCreatedAt);

        Page<Post> page = postMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return PageResult.of(page.getRecords(), page.getTotal(), pageNum, pageSize);
    }

    @Override
    public boolean toggleLike(Long userId, Long postId) {
        String key = RedisKeyConstant.POST_LIKE_USERS + postId;
        String userIdStr = String.valueOf(userId);

        Boolean isMember = redisTemplate.opsForSet().isMember(key, userIdStr);
        if (Boolean.TRUE.equals(isMember)) {
            // 取消点赞
            redisTemplate.opsForSet().remove(key, userIdStr);
            postMapper.update(null, new com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper<Post>()
                    .eq(Post::getId, postId)
                    .gt(Post::getLikeCount, 0)
                    .setSql("like_count = like_count - 1"));
            return false;
        } else {
            // 点赞
            redisTemplate.opsForSet().add(key, userIdStr);
            postMapper.update(null, new com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper<Post>()
                    .eq(Post::getId, postId)
                    .setSql("like_count = like_count + 1"));
            return true;
        }
    }

    @Override
    public void deletePost(Long userId, Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BizException(ResultCode.POST_NOT_FOUND);
        }
        if (!post.getUserId().equals(userId)) {
            throw new BizException(ResultCode.PERMISSION_DENIED);
        }

        // 逻辑删除
        postMapper.update(null, new com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper<Post>()
                .eq(Post::getId, postId)
                .set(Post::getStatus, 0));
    }

    @Override
    public PageResult<Post> searchPosts(String keyword, int pageNum, int pageSize) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1)
                .like(Post::getContent, keyword)
                .orderByDesc(Post::getCreatedAt);

        Page<Post> page = postMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return PageResult.of(page.getRecords(), page.getTotal(), pageNum, pageSize);
    }
}

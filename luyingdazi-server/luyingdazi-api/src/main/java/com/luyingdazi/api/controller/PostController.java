package com.luyingdazi.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luyingdazi.common.constant.RedisKeyConstant;
import com.luyingdazi.api.service.OssUrlService;
import com.luyingdazi.common.result.PageResult;
import com.luyingdazi.common.result.Result;
import com.luyingdazi.common.util.UserContext;
import com.luyingdazi.mapper.UserMapper;
import com.luyingdazi.model.entity.Post;
import com.luyingdazi.model.entity.User;
import com.luyingdazi.model.vo.PostVO;
import com.luyingdazi.service.social.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 动态接口
 *
 * @author luyingdazi
 */
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserMapper userMapper;
    private final StringRedisTemplate redisTemplate;
    private final OssUrlService ossUrlService;

    /**
     * 发布动态
     */
    @PostMapping("/publish")
    public Result<Long> publish(@RequestBody Post post) {
        return Result.success(postService.publishPost(UserContext.getUserId(), post));
    }

    /**
     * 动态详情（含用户信息）
     */
    @GetMapping("/{postId}")
    public Result<PostVO> getDetail(@PathVariable Long postId) {
        Post post = postService.getPostDetail(postId, UserContext.getUserId());
        PostVO vo = toPostVO(post, UserContext.getUserId());
        return Result.success(vo);
    }

    /**
     * 首页动态流（带用户信息+点赞状态）
     */
    @GetMapping("/feed")
    public Result<PageResult<PostVO>> getFeed(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword) {
        PageResult<Post> page;
        if (keyword != null && !keyword.isBlank()) {
            page = postService.searchPosts(keyword, pageNum, pageSize);
        } else {
            page = postService.getPostFeed(pageNum, pageSize);
        }
        PageResult<PostVO> voPage = convertPage(page, UserContext.getUserId());
        return Result.success(voPage);
    }

    /**
     * 用户动态列表
     */
    @GetMapping("/user/{userId}")
    public Result<PageResult<PostVO>> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageResult<Post> page = postService.getUserPosts(userId, pageNum, pageSize);
        PageResult<PostVO> voPage = convertPage(page, UserContext.getUserId());
        return Result.success(voPage);
    }

    /**
     * 点赞/取消点赞
     */
    @PostMapping("/like/{postId}")
    public Result<Boolean> toggleLike(@PathVariable Long postId) {
        return Result.success(postService.toggleLike(UserContext.getUserId(), postId));
    }

    /**
     * 删除动态
     */
    @DeleteMapping("/{postId}")
    public Result<Void> delete(@PathVariable Long postId) {
        postService.deletePost(UserContext.getUserId(), postId);
        return Result.success();
    }

    // ==================== 私有方法 ====================

    /**
     * 将 Post 列表转为带用户信息的 PostVO 列表
     */
    private PageResult<PostVO> convertPage(PageResult<Post> page, Long currentUserId) {
        List<Post> posts = page.getList();
        if (posts == null || posts.isEmpty()) {
            return PageResult.of(Collections.emptyList(), page.getTotal(), page.getPageNum(), page.getPageSize());
        }

        // 批量查用户信息
        Set<Long> userIds = posts.stream().map(Post::getUserId).collect(Collectors.toSet());
        Map<Long, User> userMap = userMapper.selectBatchIds(userIds).stream()
                .collect(Collectors.toMap(User::getId, u -> u));

        List<PostVO> voList = posts.stream()
                .map(p -> toPostVOWithUser(p, userMap, currentUserId))
                .collect(Collectors.toList());

        return PageResult.of(voList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    private PostVO toPostVO(Post post, Long currentUserId) {
        User user = userMapper.selectById(post.getUserId());
        PostVO vo = new PostVO();
        vo.setId(post.getId());
        vo.setUserId(post.getUserId());
        vo.setContent(post.getContent());
        vo.setImages(ossUrlService.toAccessibleUrls(post.getImages()));
        vo.setLocationName(post.getLocationName());
        vo.setLikeCount(post.getLikeCount());
        vo.setCommentCount(post.getCommentCount());
        vo.setIsTop(post.getIsTop());
        vo.setCreatedAt(post.getCreatedAt());
        if (user != null) {
            vo.setNickname(user.getNickname());
            vo.setAvatar(ossUrlService.toAccessibleUrl(user.getAvatar()));
        }
        // 点赞状态
        vo.setLiked(checkLiked(post.getId(), currentUserId));
        return vo;
    }

    private PostVO toPostVOWithUser(Post post, Map<Long, User> userMap, Long currentUserId) {
        PostVO vo = new PostVO();
        vo.setId(post.getId());
        vo.setUserId(post.getUserId());
        vo.setContent(post.getContent());
        vo.setImages(ossUrlService.toAccessibleUrls(post.getImages()));
        vo.setLocationName(post.getLocationName());
        vo.setLikeCount(post.getLikeCount());
        vo.setCommentCount(post.getCommentCount());
        vo.setIsTop(post.getIsTop());
        vo.setCreatedAt(post.getCreatedAt());

        User user = userMap.get(post.getUserId());
        if (user != null) {
            vo.setNickname(user.getNickname());
            vo.setAvatar(ossUrlService.toAccessibleUrl(user.getAvatar()));
        }
        vo.setLiked(checkLiked(post.getId(), currentUserId));
        return vo;
    }

    private boolean checkLiked(Long postId, Long userId) {
        if (userId == null) return false;
        String key = RedisKeyConstant.POST_LIKE_USERS + postId;
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, String.valueOf(userId)));
    }
}

package com.luyingdazi.api.controller;

import com.luyingdazi.api.service.OssUrlService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luyingdazi.common.exception.BizException;
import com.luyingdazi.common.result.PageResult;
import com.luyingdazi.common.result.Result;
import com.luyingdazi.common.result.ResultCode;
import com.luyingdazi.common.util.UserContext;
import com.luyingdazi.mapper.FavoriteMapper;
import com.luyingdazi.mapper.PostMapper;
import com.luyingdazi.model.entity.Favorite;
import com.luyingdazi.model.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** 动态收藏接口。 */
@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteMapper favoriteMapper;
    private final PostMapper postMapper;
    private final OssUrlService ossUrlService;

    @PostMapping("/toggle/{postId}")
    public Result<Boolean> toggle(@PathVariable Long postId) {
        Long userId = UserContext.getUserId();
        Post post = postMapper.selectById(postId);
        if (post == null || post.getStatus() != 1) {
            throw new BizException(ResultCode.POST_NOT_FOUND);
        }

        Favorite existing = favoriteMapper.selectOne(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId)
                .eq(Favorite::getPostId, postId));
        if (existing != null) {
            favoriteMapper.deleteById(existing.getId());
            return Result.success(false);
        }

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setPostId(postId);
        favorite.setCreatedAt(LocalDateTime.now());
        favoriteMapper.insert(favorite);
        return Result.success(true);
    }

    @GetMapping("/check/{postId}")
    public Result<Boolean> check(@PathVariable Long postId) {
        Long count = favoriteMapper.selectCount(new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, UserContext.getUserId())
                .eq(Favorite::getPostId, postId));
        return Result.success(count > 0);
    }

    @GetMapping("/list")
    public Result<PageResult<Post>> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Favorite> page = favoriteMapper.selectPage(
                new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, UserContext.getUserId())
                        .orderByDesc(Favorite::getCreatedAt));
        if (page.getRecords().isEmpty()) {
            return Result.success(PageResult.of(
                    Collections.emptyList(), page.getTotal(), pageNum, pageSize));
        }

        List<Long> ids = page.getRecords().stream().map(Favorite::getPostId).toList();
        Map<Long, Post> posts = postMapper.selectList(new LambdaQueryWrapper<Post>()
                        .in(Post::getId, ids)
                        .eq(Post::getStatus, 1))
                .stream().collect(Collectors.toMap(Post::getId, Function.identity()));
        List<Post> ordered = ids.stream().map(posts::get)
                .filter(java.util.Objects::nonNull).toList();
        ordered.forEach(post -> post.setImages(
                ossUrlService.toAccessibleUrls(post.getImages())));
        return Result.success(PageResult.of(ordered, page.getTotal(), pageNum, pageSize));
    }
}

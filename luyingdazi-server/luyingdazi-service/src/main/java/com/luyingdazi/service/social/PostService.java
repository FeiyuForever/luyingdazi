package com.luyingdazi.service.social;

import com.luyingdazi.common.result.PageResult;
import com.luyingdazi.model.entity.Post;

/**
 * 动态服务接口
 *
 * @author luyingdazi
 */
public interface PostService {

    /**
     * 发布动态
     *
     * @param userId 用户ID
     * @param post   动态内容
     * @return 动态ID
     */
    Long publishPost(Long userId, Post post);

    /**
     * 获取动态详情
     *
     * @param postId 动态ID
     * @param currentUserId 当前用户ID（判断是否点赞）
     * @return 动态详情
     */
    Post getPostDetail(Long postId, Long currentUserId);

    /**
     * 获取动态列表（首页信息流）
     * 按时间倒序，后续可改为推荐算法
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<Post> getPostFeed(int pageNum, int pageSize);

    /**
     * 获取用户的动态列表
     *
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<Post> getUserPosts(Long userId, int pageNum, int pageSize);

    /**
     * 点赞/取消点赞
     *
     * @param userId 用户ID
     * @param postId 动态ID
     * @return true=点赞, false=取消点赞
     */
    boolean toggleLike(Long userId, Long postId);

    /**
     * 删除动态
     *
     * @param userId 用户ID
     * @param postId 动态ID
     */
    void deletePost(Long userId, Long postId);

    /**
     * 搜索动态（按内容关键词）
     *
     * @param keyword  关键词
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<Post> searchPosts(String keyword, int pageNum, int pageSize);
}

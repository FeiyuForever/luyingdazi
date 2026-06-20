package com.luyingdazi.service.match;

import com.luyingdazi.model.query.NearbyQuery;
import com.luyingdazi.model.vo.UserVO;

import java.util.List;

/**
 * 匹配服务接口（附近的人、同城匹配、搜索）
 *
 * @author luyingdazi
 */
public interface MatchService {

    /**
     * 获取附近的人
     * 基于 Redis GEO 实现，按距离排序
     *
     * @param userId 当前用户ID
     * @param query  查询参数（经纬度/半径/数量/筛选条件）
     * @return 附近用户列表（含距离）
     */
    List<UserVO> getNearbyUsers(Long userId, NearbyQuery query);

    /**
     * 智能匹配推荐
     * 综合考虑距离 + 标签匹配度 + 活跃度
     *
     * @param userId 当前用户ID
     * @return 推荐用户列表
     */
    List<UserVO> getRecommendUsers(Long userId);

    /**
     * 搜索用户
     * 支持按昵称/城市/标签/性别搜索
     *
     * @param keyword  关键词
     * @param city     城市筛选（可选）
     * @param gender   性别筛选（可选）
     * @param tag      标签筛选（可选）
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 匹配用户列表
     */
    List<UserVO> searchUsers(String keyword, String city, Integer gender,
                             String tag, int pageNum, int pageSize);
}

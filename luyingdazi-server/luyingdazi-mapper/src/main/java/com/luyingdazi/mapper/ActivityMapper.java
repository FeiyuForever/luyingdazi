package com.luyingdazi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyingdazi.model.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 活动 Mapper
 *
 * @author luyingdazi
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

    @Select("""
            SELECT a.*
            FROM t_activity a
            WHERE a.user_id = #{userId}
            ORDER BY a.created_at DESC
            """)
    List<Activity> selectCreatedByUserId(@Param("userId") Long userId);

    @Select("""
            SELECT a.*
            FROM t_activity a
            INNER JOIN t_activity_member m ON m.activity_id = a.id
            WHERE m.user_id = #{userId}
              AND m.status = 1
            ORDER BY m.joined_at DESC
            """)
    List<Activity> selectJoinedByUserId(@Param("userId") Long userId);
}

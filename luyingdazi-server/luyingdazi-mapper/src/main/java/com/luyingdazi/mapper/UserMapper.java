package com.luyingdazi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyingdazi.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户 Mapper
 *
 * @author luyingdazi
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据 openid 查询用户
     */
    @Select("SELECT id, openid, nickname, avatar, gender, city, bio, camping_years, " +
            "member_level, member_expire_time, invite_code, status, created_at " +
            "FROM t_user WHERE openid = #{openid} AND status != 0")
    User selectByOpenid(@Param("openid") String openid);
}

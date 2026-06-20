package com.luyingdazi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyingdazi.model.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态 Mapper
 *
 * @author luyingdazi
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}

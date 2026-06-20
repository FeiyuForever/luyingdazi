package com.luyingdazi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyingdazi.model.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论 Mapper
 *
 * @author luyingdazi
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}

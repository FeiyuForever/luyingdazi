package com.luyingdazi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyingdazi.model.entity.ChatSession;
import org.apache.ibatis.annotations.Mapper;

/**
 * 私信会话 Mapper
 *
 * @author luyingdazi
 */
@Mapper
public interface ChatSessionMapper extends BaseMapper<ChatSession> {

}

package com.luyingdazi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luyingdazi.model.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 私信消息 Mapper
 *
 * @author luyingdazi
 */
@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

}

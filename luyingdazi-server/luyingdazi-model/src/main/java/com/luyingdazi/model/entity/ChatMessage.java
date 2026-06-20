package com.luyingdazi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 私信消息实体
 *
 * @author luyingdazi
 */
@Data
@TableName("t_chat_message")
public class ChatMessage implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long sessionId;

    private Long senderId;

    private Long receiverId;

    /** 1文本 2图片 3位置 */
    private Integer msgType;

    private String content;

    private Integer isRead;

    /** 0撤回 1正常 */
    private Integer status;

    private LocalDateTime createdAt;
}

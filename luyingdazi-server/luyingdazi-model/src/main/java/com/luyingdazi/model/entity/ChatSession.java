package com.luyingdazi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 私信会话实体
 *
 * @author luyingdazi
 */
@Data
@TableName("t_chat_session")
public class ChatSession implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userAId;

    private Long userBId;

    private String lastMsg;

    private LocalDateTime lastMsgTime;

    private Integer aUnread;

    private Integer bUnread;

    private LocalDateTime createdAt;
}

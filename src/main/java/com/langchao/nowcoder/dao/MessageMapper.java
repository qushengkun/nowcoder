package com.langchao.nowcoder.dao;

import com.langchao.nowcoder.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description: MessageMapper
 *
 * @Author: qsk
 * @Create: 2023/8/21 - 19:38
 * @version: v1.0
 */
@Mapper
public interface MessageMapper {

    // 查询当前用户的会话列表，针对每个会话只返回一条最新的消息
    List<Message> selectConversations(int userId, int offset, int limit);

    // 查询当前用户的会话数量
    int selectConversationCount(int userId);

    // 查询每个会话所包含的私信列表
    List<Message> selectLetters(String conversationId, int offset, int limit);

    // 查询每个会话包含的私信数量
    int selectLetterCount(String conversationId);

    // 查询未读的私信数量
    int selectLetterUnreadCount(int userId, String conversationId);

}

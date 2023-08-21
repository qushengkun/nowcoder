package com.langchao.nowcoder.service;

import com.langchao.nowcoder.dao.MessageMapper;
import com.langchao.nowcoder.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: MessageService
 *
 * @Author: qsk
 * @Create: 2023/8/21 - 20:24
 * @version: v1.0
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public List<Message> findConversations(int userId, int offset, int limit){
        return messageMapper.selectConversations(userId, offset, limit);
    }

    public int findConversationCount(int userId){
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId, int offset, int limit){
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    public int findLetterCount(String conversationId){
        return messageMapper.selectLetterCount(conversationId);
    }

    public int findLetterUnreadCount(int userId, String conversationId){
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

}

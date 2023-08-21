package com.langchao.nowcoder.controller;

import com.langchao.nowcoder.entity.Message;
import com.langchao.nowcoder.entity.Page;
import com.langchao.nowcoder.entity.User;
import com.langchao.nowcoder.service.MessageService;
import com.langchao.nowcoder.service.UserService;
import com.langchao.nowcoder.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: MessageController
 *
 * @Author: qsk
 * @Create: 2023/8/21 - 20:30
 * @version: v1.0
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;


    // 私信列表
    @RequestMapping(path = "/letter/list",method = RequestMethod.GET)
    public String getLetterList(Model model, Page page){

        User user = hostHolder.getUser();

        // 分页消息
        page.setLimit(5);
        page.setPath("/letter/list");
        page.setRows(messageService.findConversationCount(user.getId()));

        // 会话列表

        List<Message> conversationsList = messageService.findConversations(user.getId(), page.getOffset(), page.getLimit());

        List<Map<String, Object>> conversations = new ArrayList<>();
        if(conversationsList != null){
            for(Message message : conversationsList){

                HashMap<String, Object> map = new HashMap<>();
                map.put("conversation", message);
                map.put("letterCount", messageService.findLetterCount(message.getConversationId()));
                map.put("unreadCount", messageService.findLetterUnreadCount(user.getId(), message.getConversationId()));

                int targetId = user.getId() == message.getFromId() ? message.getToId() : message.getFromId();
                map.put("target", userService.findUserById(targetId));
                conversations.add(map);
            }
        }

        model.addAttribute("conversations",conversations);

        // 查询未读消息数量
        int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(), null);
        model.addAttribute("letterUnreadCount",letterUnreadCount);

        return "/site/letter";
    }

}

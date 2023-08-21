package com.langchao.nowcoder.controller;

import com.langchao.nowcoder.entity.Comment;
import com.langchao.nowcoder.service.CommentService;
import com.langchao.nowcoder.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * Description: CommentController
 *
 * @Author: qsk
 * @Create: 2023/8/21 - 16:33
 * @version: v1.0
 */
@Controller
@RequestMapping(path ="/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private HostHolder hostHolder;


    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId")int discussPostId, Comment comment){
        System.out.println(1);
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        return "redirect:/discuss/detail/" + discussPostId;
    }


}

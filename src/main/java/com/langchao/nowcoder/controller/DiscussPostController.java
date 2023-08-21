package com.langchao.nowcoder.controller;

import com.langchao.nowcoder.entity.Comment;
import com.langchao.nowcoder.entity.DiscussPost;
import com.langchao.nowcoder.entity.Page;
import com.langchao.nowcoder.entity.User;
import com.langchao.nowcoder.service.CommentService;
import com.langchao.nowcoder.service.DiscussPostService;
import com.langchao.nowcoder.service.UserService;
import com.langchao.nowcoder.utils.HostHolder;
import com.langchao.nowcoder.utils.NowcoderConstant;
import com.langchao.nowcoder.utils.NowcoderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Description: DiscussPostController
 *
 * @Author: qsk
 * @Create: 2023/8/20 - 16:27
 * @version: v1.0
 */

@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements NowcoderConstant {


    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title,String content){
        User user = hostHolder.getUser();
        if(user == null){
            return NowcoderUtil.getJSONString(403,"您还没有登录");
        }

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discussPostService.insertDiscussPost(post);

        // 如果有报错的情况，后边统一处理
        return NowcoderUtil.getJSONString(0,"发布成功");

    }

    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDiscussPostDetail(@PathVariable("discussPostId")int discussPostId, Model model, Page page){

        // 帖子
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post",post);
        // 帖子作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user",user);

        // 帖子评论信息
        page.setLimit(5);
        page.setPath("/discuss/detail/" + discussPostId);
        page.setRows(post.getCommentCount());


        // 评论列表
        List<Comment> commentList = commentService.findCommentsByEntity(
                ENTITY_TYPE_COMMENT, post.getId(), page.getOffset(), page.getLimit());

        // 为了可以显示评论和作者，将评论和作者放到map中，这些评论和对应的作者组成列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if(commentList != null){
            for(Comment comment : commentList){
                Map<String, Object> commentVo = new HashMap<>();
                // 评论
                commentVo.put("comment",comment);
                // 作者
                commentVo.put("user",userService.findUserById(comment.getUserId()));

                // 对每个评论，所对应的回复列表
                List<Comment> replayList = commentService.findCommentsByEntity(
                        ENTITY_TYPE_REPLAY, comment.getId(), 0, Integer.MAX_VALUE);


                List<Map<String, Object>> replayVoList = new ArrayList<>();
                if(replayList != null){
                    for(Comment replay : replayList){
                        Map<String, Object> replayVo = new HashMap<>();
                        // 回复
                        replayVo.put("replay",replay);
                        // 作者
                        replayVo.put("user",userService.findUserById(replay.getUserId()));
                        // 回复目标
                        User targetUser = replay.getTargetId() == 0 ? null : userService.findUserById(replay.getTargetId());
                        replayVo.put("target",targetUser);

                        replayVoList.add(replayVo);
                    }
                }
                commentVo.put("replays",replayVoList);

                // 回复数量
                int replayCount = commentService.findCommentCount(ENTITY_TYPE_REPLAY, comment.getId());
                commentVo.put("replyCount",replayCount);

                commentVoList.add(commentVo);

            }

        }
        model.addAttribute("comments",commentVoList);

        return "/site/discuss-detail";

    }

}

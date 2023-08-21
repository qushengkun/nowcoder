package com.langchao.nowcoder.service;

import com.langchao.nowcoder.dao.DiscussPostMapper;
import com.langchao.nowcoder.entity.DiscussPost;
import com.langchao.nowcoder.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Description: DiscussPostService
 *
 * @Author: qsk
 * @Create: 2023/8/12 - 11:52
 * @version: v1.0
 */
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit){
        return discussPostMapper.selectDiscussPosts(userId,offset,limit);
    }

    public int findDiscussPostsRows(int userId){
        return discussPostMapper.selectDiscussPostsRows(userId);
    }


    public int insertDiscussPost(DiscussPost post){
        if(post == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        System.out.println(post);

        // 转移 html 标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPosts(post);
    }


    public DiscussPost findDiscussPostById(int id){
        return discussPostMapper.selectDiscussPostById(id);
    }

    public int updateCommentCount(int id, int commentCount){
        return discussPostMapper.updateCommentCount(id, commentCount);
    }


}

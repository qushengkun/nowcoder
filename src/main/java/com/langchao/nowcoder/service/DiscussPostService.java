package com.langchao.nowcoder.service;

import com.langchao.nowcoder.dao.DiscussPostMapper;
import com.langchao.nowcoder.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit){
        return discussPostMapper.selectDiscussPosts(userId,offset,limit);
    }

    public int findDiscussPostsRows(int userId){
        return discussPostMapper.selectDiscussPostsRows(userId);
    }

}

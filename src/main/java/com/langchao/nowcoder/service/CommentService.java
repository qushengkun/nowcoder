package com.langchao.nowcoder.service;

import com.langchao.nowcoder.dao.CommentMapper;
import com.langchao.nowcoder.entity.Comment;
import com.langchao.nowcoder.utils.NowcoderConstant;
import com.langchao.nowcoder.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;


/**
 * Description: CommentService
 *
 * @Author: qsk
 * @Create: 2023/8/20 - 22:17
 * @version: v1.0
 */
@Service
public class CommentService implements NowcoderConstant {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Autowired
    private DiscussPostService discussPostService;


    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit){
        return commentMapper.selectCommentsByEntity(entityType, entityId, offset, limit);
    }

    public int findCommentCount(int entityType, int entityId){
        return commentMapper.selectCountByEntity(entityType, entityId);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int addComment(Comment comment){

        if(comment==null){
            throw new IllegalArgumentException("参数不能为空");
        }

        // 添加评论
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        int rows = commentMapper.insertComment(comment);

        // 更新帖子的评论数
        if(comment.getEntityType() == ENTITY_TYPE_COMMENT){
            int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(), count);
        }

        return rows;



    }

}

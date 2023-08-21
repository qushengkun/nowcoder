package com.langchao.nowcoder.dao;

import com.langchao.nowcoder.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Description: CommentMapper
 *
 * @Author: qsk
 * @Create: 2023/8/20 - 22:05
 * @version: v1.0
 */

@Mapper
public interface CommentMapper {

    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType,int entityId);

    int insertComment(Comment comment);



}

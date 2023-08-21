package com.langchao.nowcoder.dao;

import com.langchao.nowcoder.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description: DiscussMapper
 *
 * @Author: qsk
 * @Create: 2023/8/12 - 11:22
 * @version: v1.0
 */
@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);

    // @Param 注解用于给参数取别名
    // 如果只有一个参数，并且在 <if> 中使用，则必须加别名
    int selectDiscussPostsRows(@Param("userId") int userId);

    int insertDiscussPosts(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id,int commentCount);

}

package com.langchao.nowcoder.dao;
import com.langchao.nowcoder.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description: UserMapper
 *
 * @Author: qsk
 * @Create: 2023/8/11 - 16:30
 * @version: v1.0
 */

@Mapper
public interface UserMapper {

    User selectById(int id);
    User selectByName(String name);
    User selectByEmail(String email);
    int insertUser(User user);
    int updateStatus(int id, int status);
    int updateHeader(int id, String headerUrl);
    int updatePassword(int id, String password);

}

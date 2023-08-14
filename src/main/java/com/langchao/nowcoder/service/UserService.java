package com.langchao.nowcoder.service;

import com.langchao.nowcoder.dao.UserMapper;
import com.langchao.nowcoder.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: UserService
 *
 * @Author: qsk
 * @Create: 2023/8/12 - 11:58
 * @version: v1.0
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }

}

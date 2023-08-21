package com.langchao.nowcoder.service;

import com.langchao.nowcoder.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: AccountService
 *
 * @Author: qsk
 * @Create: 2023/8/17 - 14:56
 * @version: v1.0
 */

@Service
public class AccountService {

    @Autowired
    private UserMapper userMapper;

    public int updateHeader(int userId,String headerUrl){
        return userMapper.updateHeader(userId, headerUrl);
    }


}

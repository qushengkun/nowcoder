package com.langchao.nowcoder.utils;

import com.langchao.nowcoder.entity.Page;
import com.langchao.nowcoder.entity.User;
import org.springframework.stereotype.Component;

/**
 * Description: HostHolder
 * 持有用户的信息 用于代替 session 对象
 * @Author: qsk
 * @Create: 2023/8/17 - 11:12
 * @version: v1.0
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<User>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }

}

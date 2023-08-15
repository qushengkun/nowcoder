package com.langchao.nowcoder.service;

import com.langchao.nowcoder.dao.UserMapper;
import com.langchao.nowcoder.entity.User;
import com.langchao.nowcoder.utils.MailClient;
import com.langchao.nowcoder.utils.NowcoderConstant;
import com.langchao.nowcoder.utils.NowcoderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Description: UserService
 *
 * @Author: qsk
 * @Create: 2023/8/12 - 11:58
 * @version: v1.0
 */
@Service
public class UserService implements NowcoderConstant {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${nowcoder.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }


    public Map<String,Object> register(User user){
        Map<String,Object> map = new HashMap<>();

        // 空值处理
        if(user == null){
            throw new IllegalArgumentException("参数不能为空");
        }

        if(StringUtils.isBlank(user.getUsername())){
            map.put("UsernameMsg","username不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("PasswordMsg","Password不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("EmailMsg","Email不能为空");
            return map;
        }

        // 验证账号
        // 判断用户名，邮箱是否已经存在
        User u = userMapper.selectByName(user.getUsername());
        if(u != null){
            map.put("UsernameMsg","用户名已经存在");
            return map;
        }
        u = userMapper.selectByEmail(user.getEmail());
        if(u != null){
            map.put("EmailMsg","邮箱已经存在");
            return map;
        }


        // 注册用户
        user.setSalt(NowcoderUtil.generateUUID().substring(0,5));
        user.setPassword(NowcoderUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(NowcoderUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        // 给邮箱发送验证码
        Context context = new Context();
        context.setVariable("email",user.getEmail());

        // url = http://localhost:8080/nowcoder/activation/151/code

        String url = domain + contextPath + "/activation/"+ user.getId() + "/" + user.getActivationCode();
        context.setVariable("url",url);

        String process = templateEngine.process("mail/activation", context);

        mailClient.sendMail(user.getEmail(),"激活邮件",process);

        return map;
    }



    // 返回激活状态
    public int activationStatus(int userId,String code){
        User user = userMapper.selectById(userId);
        if(user.getStatus() == 1){
            return ACTIVATION_REPEAt;
        }else if(user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        }else{
            return ACTIVATION_FAILED;
        }
    }

}

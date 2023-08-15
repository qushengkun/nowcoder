package com.langchao.nowcoder.controller;

import com.langchao.nowcoder.entity.User;
import com.langchao.nowcoder.service.UserService;
import com.langchao.nowcoder.utils.NowcoderConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Description: LoginController
 *
 * @Author: qsk
 * @Create: 2023/8/15 - 15:50
 * @version: v1.0
 */

@Controller
public class LoginController implements NowcoderConstant {


    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register",method= RequestMethod.GET)
    public String getRegisterPage(){
        return "site/register";
    }


    @RequestMapping(path = "/login",method= RequestMethod.GET)
    public String getLoginPage(){
        return "site/login";
    }


    // 点击 注册 以后得逻辑
    @RequestMapping(path = "/register_submit",method = RequestMethod.POST)
    public String registerSubmit(Model model, User user){

        Map<String, Object> map = userService.register(user);
        // 注册成功
        if(map == null || map.isEmpty()){
            model.addAttribute("msg","注册成功，我们已经向您的邮箱发送了激活邮件,请尽快激活");
            model.addAttribute("target","/index");
            return "/site/operate-result.html";
        }
        // 注册不成功
        else{
            model.addAttribute("UsernameMsg",map.get("UsernameMsg"));
            model.addAttribute("PasswordMsg",map.get("PasswordMsg"));
            model.addAttribute("EmailMsg",map.get("EmailMsg"));
            return "/site/register";
        }
    }



    @RequestMapping(path = "/activation/{userId}/{code}",method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId,@PathVariable("code") String code){

        int result = userService.activationStatus(userId, code);
        if(result == ACTIVATION_SUCCESS){
            model.addAttribute("msg","激活成功，您可以使用该账户了");
            model.addAttribute("target","/login");
        }else if(result == ACTIVATION_REPEAt){
            model.addAttribute("msg","无效操作，请不要重复激活");
            model.addAttribute("target","/index");
        }else{
            model.addAttribute("msg","激活失败");
            model.addAttribute("target","/index");
        }
        return "site/operate-result";

    }


}

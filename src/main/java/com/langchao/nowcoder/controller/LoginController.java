package com.langchao.nowcoder.controller;

import com.google.code.kaptcha.Producer;
import com.langchao.nowcoder.entity.User;
import com.langchao.nowcoder.service.UserService;
import com.langchao.nowcoder.utils.NowcoderConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Value("${server.servlet.context-path}")
    private String contextPath;

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


    @RequestMapping(path = "/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse resp, HttpSession session){

        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage bufferedImage = kaptchaProducer.createImage(text);

        // 将验证码存入session
        session.setAttribute("kaptcha",text);

        // 将图片输出给浏览器
        resp.setContentType("image/png");
        try {
            ServletOutputStream os = resp.getOutputStream();
            ImageIO.write(bufferedImage,"png",os);
        } catch (IOException e) {
           logger.error("相应验证码失败"+e.getMessage());
        }
    }

    @RequestMapping(path="/login",method = RequestMethod.POST)
    public String login(String username,String password, String code, boolean rememberme,
                        Model model,HttpSession session,HttpServletResponse resp){
        // 先判断验证码
        String kaptcha = (String) session.getAttribute("kaptcha");
        if(StringUtils.isBlank(code) || StringUtils.isBlank(kaptcha) || !kaptcha.equalsIgnoreCase(code)){
            model.addAttribute("codeMsg","验证码不正确");
            return "/site/login";
        }

        // 检查账号,密码
        int expiredSeconds = rememberme ? REMEMBER_EXPIRED_SECONDS:DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            resp.addCookie(cookie);
            return "redirect:/index";
        }else{
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            return "/site/login";
        }

    }

    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket")String ticket){
        userService.logout(ticket);
        return "redirect:/login";
    }


}

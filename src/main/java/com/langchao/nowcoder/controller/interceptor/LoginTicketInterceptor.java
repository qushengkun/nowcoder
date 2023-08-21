package com.langchao.nowcoder.controller.interceptor;

import com.langchao.nowcoder.entity.LoginTicket;
import com.langchao.nowcoder.entity.User;
import com.langchao.nowcoder.service.UserService;
import com.langchao.nowcoder.utils.CookieUtil;
import com.langchao.nowcoder.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Description: LoginTicketInterceptor
 *
 * @Author: qsk
 * @Create: 2023/8/17 - 10:53
 * @version: v1.0
 */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 cookie 中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");
        if(ticket != null){
            // 查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            // 检查凭证是否有效
            if(loginTicket !=null && loginTicket.getStatus()!=0 && loginTicket.getExpired().after(new Date())){
                // 根据凭证查询 user
                User user = userService.findUserById(loginTicket.getUserId());

                // 在本次请求中 持有用户
                hostHolder.setUser(user);
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if(user != null && modelAndView != null){
            modelAndView.addObject("loginUser",user);
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}

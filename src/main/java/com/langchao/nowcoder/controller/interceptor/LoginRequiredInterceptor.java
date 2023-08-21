package com.langchao.nowcoder.controller.interceptor;

import com.langchao.nowcoder.annotation.LoginRequired;
import com.langchao.nowcoder.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Description: LoginRequiredInterceptor
 *
 * @Author: qsk
 * @Create: 2023/8/17 - 18:01
 * @version: v1.0
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            // 自定义注解不为空（使用该方法需要登录），但是没有登录的，
            if(loginRequired != null && hostHolder.getUser() == null ){
                // 重定向到登录页面
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }
}

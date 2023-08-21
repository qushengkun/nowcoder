package com.langchao.nowcoder.config;

import com.langchao.nowcoder.controller.interceptor.AlphaInterceptor;
import com.langchao.nowcoder.controller.interceptor.LoginRequiredInterceptor;
import com.langchao.nowcoder.controller.interceptor.LoginTicketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description: WebMvcConfig
 *
 * @Author: qsk
 * @Create: 2023/8/17 - 10:30
 * @version: v1.0
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AlphaInterceptor alphaInterceptor;

    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;


    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(alphaInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.png","/**/*.js","/**/*.jpeg")
                .addPathPatterns("/register","/login");


        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.png","/**/*.js","/**/*.jpeg");


        registry.addInterceptor(loginRequiredInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.png","/**/*.js","/**/*.jpeg");


    }


}

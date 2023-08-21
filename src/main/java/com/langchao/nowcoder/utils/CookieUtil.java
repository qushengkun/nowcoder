package com.langchao.nowcoder.utils;

import org.apache.coyote.Request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * Description: CookieUtil
 *
 * @Author: qsk
 * @Create: 2023/8/17 - 11:00
 * @version: v1.0
 */
public class CookieUtil {

    public static String getValue(HttpServletRequest request,String name){
        if(request == null || name == null){
            throw new IllegalArgumentException("参数为空");
        }
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie:cookies) {
                if(cookie.getName().equals(name)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

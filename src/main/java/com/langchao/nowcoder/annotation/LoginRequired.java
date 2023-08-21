package com.langchao.nowcoder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: LoginRequired
 *
 * @Author: qsk
 * @Create: 2023/8/17 - 17:58
 * @version: v1.0
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

}

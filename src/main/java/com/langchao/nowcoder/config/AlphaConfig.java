package com.langchao.nowcoder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * Description: AlphaConfig
 *
 * @Author: qsk
 * @Create: 2023/8/10 - 22:20
 * @version: v1.0
 */

@Configuration
public class AlphaConfig {

    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    }
}

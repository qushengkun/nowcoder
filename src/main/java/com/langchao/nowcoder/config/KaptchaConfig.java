package com.langchao.nowcoder.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.code.kaptcha.util.Config;

import java.util.Properties;

/**
 * Description: KaptchaConfig
 *
 * @Author: qsk
 * @Create: 2023/8/16 - 10:11
 * @version: v1.0
 */

@Configuration
public class KaptchaConfig {


    @Bean
    public Producer kaptchaProducer(){

        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width","100");
        properties.setProperty("kaptcha.image.height","40");
        properties.setProperty("kaptcha.textproducer.font.size","32");
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");
        // 验证码的取值范围
        String scope = "0123456789abcdefghijklmnopqrstuvwxyz";
        properties.setProperty("kaptcha.textproducer.char.string",scope);
        properties.setProperty("kaptcha.textproducer.char.length","4");
        // 噪音的实现方式
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");


        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;





    }




}

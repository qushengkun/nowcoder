package com.langchao.nowcoder;

import com.langchao.nowcoder.utils.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Description: MailTests
 *
 * @Author: qsk
 * @Create: 2023/8/15 - 12:17
 * @version: v1.0
 */

@SpringBootTest
@ContextConfiguration(classes = NowcoderApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("821707065@qq.com","First","使用服务器发送的邮件");
    }


    @Test
    public void testHtmlMail(){

        Context context = new Context();
        context.setVariable("username","曲升坤");

        String content = templateEngine.process("mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("821707065@qq.com","html","使用服务器发送的Html邮件");

    }
}

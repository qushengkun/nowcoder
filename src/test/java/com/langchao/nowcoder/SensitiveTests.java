package com.langchao.nowcoder;

import com.langchao.nowcoder.utils.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Description: SensitiveTests
 *
 * @Author: qsk
 * @Create: 2023/8/19 - 21:37
 * @version: v1.0
 */

@SpringBootTest
@ContextConfiguration(classes = NowcoderApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter1(){
        String text1 = "在这里可以赌博，嫖娼，溜冰，开票";
        String filteredText1 = sensitiveFilter.filter(text1);
        System.out.println(filteredText1);

        String text2 = "在这里可以♡赌♡博♡，♡嫖♡娼♡，♡溜♡冰♡，♡开♡票♡";
        String filteredText2 = sensitiveFilter.filter(text2);
        System.out.println(filteredText2);
    }
}

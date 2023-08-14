package com.langchao.nowcoder;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Description: LoggerTests
 *
 * @Author: qsk
 * @Create: 2023/8/14 - 12:29
 * @version: v1.0
 */
@SpringBootTest
@ContextConfiguration(classes = NowcoderApplication.class)
public class LoggerTests {

    private static final Logger logger = LoggerFactory.getLogger(LoggerTests.class);

    @Test
    public void testLogger(){
        System.out.println(logger.getName());
        logger.debug("debugLogger");
        logger.info("infoLogger");
        logger.warn("warnLogger");
        logger.error("errorLogger");
    }

}

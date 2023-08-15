package com.langchao.nowcoder.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * Description: NowcoderUtil
 *
 * @Author: qsk
 * @Create: 2023/8/15 - 16:10
 * @version: v1.0
 */
public class NowcoderUtil {

    // 生成随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    // MD5加密
    // 在加密的字符串后 在随机添加一段字符串
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }



}

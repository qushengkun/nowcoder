package com.langchao.nowcoder.utils;

/**
 * Description: NowcoderConstant
 *
 * @Author: qsk
 * @Create: 2023/8/15 - 18:07
 * @version: v1.0
 */
public interface NowcoderConstant {


    int ACTIVATION_SUCCESS = 0;

    int ACTIVATION_REPEAt = 1;

    int ACTIVATION_FAILED = 2;

    // 默认状态的登录凭证的超时时间
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    // 记住转态的登录凭证的时间
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 30;

    // 实体类型 评论
    int ENTITY_TYPE_COMMENT= 1;

    // 实体类型 回复
    int ENTITY_TYPE_REPLAY = 2;

}

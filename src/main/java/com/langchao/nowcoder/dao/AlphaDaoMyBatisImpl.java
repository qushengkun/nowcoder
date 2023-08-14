package com.langchao.nowcoder.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * Description: AlphaDaoMyBatisImpl
 *
 * @Author: qsk
 * @Create: 2023/8/10 - 22:04
 * @version: v1.0
 */

@Primary
@Repository
public class AlphaDaoMyBatisImpl implements AlphaDao{
    @Override
    public String select() {
        return "MyBatis";
    }
}

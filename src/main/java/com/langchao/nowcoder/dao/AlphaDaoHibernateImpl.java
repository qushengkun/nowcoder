package com.langchao.nowcoder.dao;

import org.springframework.stereotype.Repository;

/**
 * Description: AlphaDaoHibernateImpl
 *
 * @Author: qsk
 * @Create: 2023/8/10 - 22:00
 * @version: v1.0
 */

@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}

package com.langchao.nowcoder.service;

import com.langchao.nowcoder.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Description: AlphaService
 *
 * @Author: qsk
 * @Create: 2023/8/10 - 22:11
 * @version: v1.0
 */
@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    public String find(){
        return alphaDao.select();
    }

    public AlphaService(){
        System.out.println("实例化 AlphaService");
    }

    @PostConstruct
    public void init(){
        System.out.println("初始化 AlphaService");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("销毁 AlphaService");
    }

}

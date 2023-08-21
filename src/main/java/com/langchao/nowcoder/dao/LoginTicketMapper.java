package com.langchao.nowcoder.dao;

import com.langchao.nowcoder.entity.LoginTicket;
import org.apache.ibatis.annotations.*;


/**
 * Description: LoginTicketMapper
 *
 * @Author: qsk
 * @Create: 2023/8/16 - 11:23
 * @version: v1.0
 */
@Mapper
public interface LoginTicketMapper {

    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);



    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectLoginTicket(String ticket);

    @Update({
            // 如果要拼接 if,要使用在 <script> 中
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
    })
    int updateLoginTicket(String ticket,int status);

}

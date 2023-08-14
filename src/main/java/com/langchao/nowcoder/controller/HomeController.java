package com.langchao.nowcoder.controller;

import com.langchao.nowcoder.entity.DiscussPost;
import com.langchao.nowcoder.entity.Page;
import com.langchao.nowcoder.entity.User;
import com.langchao.nowcoder.service.DiscussPostService;
import com.langchao.nowcoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: HomeController
 *
 * @Author: qsk
 * @Create: 2023/8/12 - 12:07
 * @version: v1.0
 */
@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        // 方法调用前，SpringMVC 会自动实例化 model 和 page,并将 Page 注入到 Model
        // 所以，在 thymeleaf 中可以直接访问 page对象的数据。
        page.setRows(discussPostService.findDiscussPostsRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, 0, 10);
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "index";
    }







}

package com.langchao.nowcoder.controller;

import com.langchao.nowcoder.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Description: AlphaController
 *
 * @Author: qsk
 * @Create: 2023/8/10 - 21:47
 * @version: v1.0
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {


    // 相应异步请求
    @RequestMapping(path="/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",30);
        emp.put("salary",6000);
        return emp;
    }

    @RequestMapping(path="/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        ArrayList<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> emp1 = new HashMap<>();
        emp1.put("name","张三");
        emp1.put("age",30);
        emp1.put("salary",6000);
        list.add(emp1);

        Map<String,Object> emp2 = new HashMap<>();
        emp2.put("name","李四");
        emp2.put("age",40);
        emp2.put("salary",7000);
        list.add(emp2);

        return list;
    }


    // 相应 html 数据
    @RequestMapping(path="/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",30);
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping(path="/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","长安大学");
        model.addAttribute("age",72);
        return "demo/view";
    }



    // POST 请求
    @RequestMapping(path="/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }


    // GET 请求
    // 方式一
    // students?current=1&limit=30
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current",required = false,defaultValue = "1") int current,
            @RequestParam(name="limit",required = false,defaultValue = "10") int limit){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // 方式二
    @RequestMapping(path="/student/{id}", method=RequestMethod.GET)
    @ResponseBody
    public String getStudent(
            @PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }



    @RequestMapping("/http")
    public void http(HttpServletRequest req, HttpServletResponse resp){
//        System.out.println(req.getMethod());
//        System.out.println(req.getServletPath());
        Enumeration<String> headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String header_name = headerNames.nextElement();
            String header_value = req.getHeader(header_name);
            System.out.println(header_name + "：" + header_value);
        }
//        System.out.println(req.getParameter("code"));

        // 返回相应数据
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = null;
        try{
            writer = resp.getWriter();
            writer.write("牛客网");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }


    }

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/data")
    @ResponseBody
    public String getDate(){
        return alphaService.find();
    }

    @RequestMapping("/hello")
    @ResponseBody
    private String sayHello(){
        return "hello,spring boot";
    }

}

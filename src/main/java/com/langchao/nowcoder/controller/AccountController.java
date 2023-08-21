package com.langchao.nowcoder.controller;

import com.langchao.nowcoder.annotation.LoginRequired;
import com.langchao.nowcoder.entity.User;
import com.langchao.nowcoder.service.AccountService;
import com.langchao.nowcoder.utils.HostHolder;
import com.langchao.nowcoder.utils.NowcoderUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Description: AccountController
 *
 * @Author: qsk
 * @Create: 2023/8/17 - 14:45
 * @version: v1.0
 */

@Controller
@RequestMapping(path = "/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Value("${nowcoder.path.upload}")
    private String uploadPath;

    @Autowired
    private HostHolder hostHolder;

    @Value("${nowcoder.path.domain}")
    private String domin;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private AccountService accountService;


    @LoginRequired
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }


    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model){

        if(headerImage == null){
            model.addAttribute("error","您还没有选择文件");
            return "/site/setting";
        }

        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if(StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件的格式不正确");
            return "/site/setting";
        }

        // 生成文件名
        fileName = NowcoderUtil.generateUUID() + suffix;
        // 确定文件的路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            // 存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败，" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常");
        }

        // 更新当前用户的头像的路径 (web 访问路径)
        // http://localhost:8080/nowcoder/account/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domin + contextPath + "/account/header/" + fileName;

        accountService.updateHeader(user.getId(),headerUrl);

        return "redirect:/index";

    }


    /**
     * 获取图像
     * @param fileName
     * @param resp
     */

    @RequestMapping(path="/header/{fileName}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName")String fileName, HttpServletResponse resp){

        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 响应图片
        resp.setContentType("image/" + suffix);
        try(
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = resp.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while((b=fis.read(buffer)) !=-1){
                os.write(buffer,0,b);
            }

        } catch (IOException e) {
            logger.error("读取头像失败" + e.getMessage());
        }
    }





}



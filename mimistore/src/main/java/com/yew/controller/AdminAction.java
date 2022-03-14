package com.yew.controller;

import com.yew.pojo.Admin;
import com.yew.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ${李森林}
 * @date 2021/12/15 20 09 27
 * @discription
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {
    //切记： 在所有的控制层里， 一定要有业务层的对象。
    @Resource
    private AdminService adminService;

    //实现登录判断，并进行相应的跳转。
    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin = adminService.login(name,pwd);
        if(admin != null){
            //登录成功
            request.setAttribute("admin",admin);
            return "main";
        }else{
            //登录失败
            request.setAttribute("errmsg","用户名或密码错误，请重新输入");
            return "login";
        }
    }
}

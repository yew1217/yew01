package com.yew.service;

import com.yew.pojo.Admin;

/**
 * @author ${李森林}
 * @date 2021/12/15 19 39 47
 * @discription
 */
public interface AdminService {
    //完成登录判断
    Admin login(String name, String pwd);
}

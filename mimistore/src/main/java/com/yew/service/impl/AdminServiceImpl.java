package com.yew.service.impl;

import com.yew.mapper.AdminMapper;
import com.yew.pojo.Admin;
import com.yew.pojo.AdminExample;
import com.yew.service.AdminService;
import com.yew.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ${李森林}
 * @date 2021/12/15 19 41 25
 * @discription
 */
@Service
public class AdminServiceImpl implements AdminService {
    //在业务逻辑层中，一定会有数据访问层的对象。
    @Resource
    private AdminMapper adminMapper;
    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户，到DB中去查询相应用户对象
        //如果有条件，则一定要创建AdminExample的对象，用来封装条件
        AdminExample example = new AdminExample();
        /**
         * 如何添加条件
         * select * from admin where a_name = "admin"
         */
        //添加用户名 a_name 条件
        example.createCriteria().andANameEqualTo(name);
        List<Admin> list = adminMapper.selectByExample(example);
        //查询到用户对象，再进行密码的比对。
        if(list.size() > 0 ){
            Admin admin = list.get(0);
            String miPwd = MD5Util.getMD5(pwd);
            if(miPwd.equals(admin.getaPass())){
                return admin;
            }
        }
        return  null;
    }
}

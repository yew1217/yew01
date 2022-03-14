package com.yew.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yew.mapper.ProductInfoMapper;
import com.yew.pojo.ProductInfo;
import com.yew.pojo.vo.ProductInfoVo;
import com.yew.service.ProductInfoService;
import com.yew.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author ${李森林}
 * @date 2021/12/16 14 23 52
 * @discription
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    public static final int Page_Size = 5;
    @Resource
    private ProductInfoService productInfoService;
    String saveFileName = "";

    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return  "product";
    }

    //显示商品第一页
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if(vo != null) {
            info = productInfoService.splitPageVo((ProductInfoVo)vo , Page_Size);
            request.getSession().removeAttribute("prodVo");
        }else {
            info = productInfoService.splitPage(1,Page_Size);
        }
        request.setAttribute("info",info);
        return "product";
    }

    //ajax 分页翻页处理
    @ResponseBody
    @RequestMapping(("/ajaxsplit"))
    public void ajaxSplit(ProductInfoVo vo , HttpSession session){
        //取得当前page参数的的页面的数据
        PageInfo info = productInfoService.splitPageVo(vo,Page_Size);
        session.setAttribute("info",info);
        saveFileName = "";
    }


    //异步ajax文件上传处理
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request){
        //提取生成文件名UUID+上传文件的后缀.jpg .png
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());

        //得到项目中图片存储路径
        String path = request.getServletContext().getRealPath("/image_big");

        //转存
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返客户端Json对象
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);
        return  object.toString();
    }

    //新增数据模块
    @RequestMapping("/save")
    public String save(ProductInfo info , HttpServletRequest request){
        info.setpDate(new Date());
        info.setpImage(saveFileName);
        int nums = -1;
        try {
            nums = productInfoService.save(info);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(nums > 0){
            request.setAttribute("msg","添加商品成功！");
        }else {
            request.setAttribute("msl","新增商品失败！");
        }
        //清空saveFileName中的值，为了下次增加或修改的异步ajax的上传处理
        saveFileName = "";
        //增加成功后，应该重新访问数据库，所以要跳转到分页显示的action上。
        return "forward:/prod/split.action";
    }

    //更新页面的数据回显 Model
    @RequestMapping("/one")
    public String one(int pid ,ProductInfoVo vo , Model model , HttpSession session){
        ProductInfo info = productInfoService.getById(pid);
        model.addAttribute("prod",info);
        session.setAttribute("prodVo" ,vo);
        return "update";
    }

    //更新页面数据功能
    @RequestMapping("/update")
    public String update(ProductInfo info , HttpServletRequest request){
        /*
            因为ajax的异步图片上传,如果有上传过，则saveFileName里有上传上来的图片的名称，
            如果没有使用异步ajax上传过图片,则saveFileNme="",
            实体类info使用隐藏表单域提供上来的pImage原始图片的名称;
         */
        if(!saveFileName.equals("")) {
            info.setpImage(saveFileName);

        }
            info.setpDate(new Date());
        int nums = -1;
        try {
            nums = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(nums > 0){
            request.setAttribute("msg","更新数据成功！");
        }else {
            request.setAttribute("msg","更新数据失败！");
        }
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    //单个商品的删除
    @RequestMapping("/delete")
    public String deleteById(int pid , ProductInfoVo vo,HttpServletRequest request){
        int nums = -1;
        try {
            nums = productInfoService.deleteById(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(nums > 0 ){
            //删除成功
            request.setAttribute("msg","删除成功！");
            request.getSession().setAttribute("deleteProdVo",vo);
        }else {
            //删除失败
            request.setAttribute("msg","删除失败！");

        }
        return "forward:/prod/deleteAjaxSplit.action";

    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit" , produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteProdVo");
        if(vo != null){
            info = productInfoService.splitPageVo((ProductInfoVo)vo,Page_Size);
        }else{
            info = productInfoService.splitPage(1,Page_Size);
        }
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }

    //批量删除
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids , HttpServletRequest request){
        String[] ps = pids.split(",");

        try {
            int nums = productInfoService.deleteBatch(ps);
            if(nums > 0){
                //批量删除成功
                request.setAttribute("msg","批量删除成功！");
            }else {
                //批量删除失败
                request.setAttribute("msg","批量删除失败！");
            }
        } catch (Exception e) {
            request.setAttribute("msg","该选中商品无法删除！");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }





}

package com.yew.service;

import com.github.pagehelper.PageInfo;
import com.yew.pojo.ProductInfo;
import com.yew.pojo.vo.ProductInfoVo;

import java.util.List;

/**
 * @author ${李森林}
 * @date 2021/12/15 22 41 08
 * @discription
 */
public interface ProductInfoService {
    //显示所有的商品信息（不分页）
    List<ProductInfo> getAll();

    //分页功能的实现
    PageInfo splitPage(int pageNum, int pageSize);

    //添加商品的事项
    int  save(ProductInfo info);

    //更新商品前的数据回显
    ProductInfo getById(int pid);

    //更新商品信息
    int update(ProductInfo info);

    //单个商品删除
    int deleteById(int pid);

    //批量删除
    int deleteBatch(String[] ids);

    //多条件查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件查询分页
    public PageInfo splitPageVo(ProductInfoVo vo , int pageSize);
}

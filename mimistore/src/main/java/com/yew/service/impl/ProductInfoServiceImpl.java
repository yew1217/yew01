package com.yew.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yew.mapper.ProductInfoMapper;
import com.yew.pojo.ProductInfo;
import com.yew.pojo.ProductInfoExample;
import com.yew.pojo.vo.ProductInfoVo;
import com.yew.service.ProductInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ${李森林}
 * @date 2021/12/15 22 42 23
 * @discription
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Resource
    private ProductInfoMapper productInfoMapper;
    //查询所有的商品（不分页）
    @Override
    public List<ProductInfo> getAll() {
       return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    /*
        分页功能
        select * from product_info limit 起始记录数=（（当前页-1）*每页的条数），每页的条数、
        select * form product_iifo limit 10.5
     */
    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用pageHelper工具类完成分页设置
        PageHelper.startPage(pageNum,pageSize);

        //进行pageInfo的数据封装
        //进行有条件的查询时，必须要创建ProductInfoExample对象。
        ProductInfoExample productInfoExample = new ProductInfoExample();

        //设置排序，按主键降序排序 : 让新添加的数据显示在第一页第一行，方便查看。
        //select * from product_info order by p_id desc
        productInfoExample.setOrderByClause("p_id desc");

        //取集合
        List<ProductInfo> list = productInfoMapper.selectByExample(productInfoExample);

        //将获取到的数据封装到PageInfo中去
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int deleteById(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo){
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);
    }
}

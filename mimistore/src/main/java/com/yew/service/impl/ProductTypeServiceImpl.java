package com.yew.service.impl;

import com.yew.mapper.ProductTypeMapper;
import com.yew.pojo.ProductType;
import com.yew.pojo.ProductTypeExample;
import com.yew.service.ProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ${李森林}
 * @date 2021/12/16 19 54 46
 * @discription
 */
@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Resource
    private ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}

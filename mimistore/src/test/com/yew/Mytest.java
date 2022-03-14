package com.yew;

import com.yew.mapper.ProductInfoMapper;
import com.yew.pojo.ProductInfo;
import com.yew.pojo.vo.ProductInfoVo;
import com.yew.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ${李森林}
 * @date 2021/12/15 17 50 51
 * @discription
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class Mytest {
    @Resource
    private ProductInfoMapper mapper;

    @Test
    public void testmd5(){
        String mi = MD5Util.getMD5("000000");
        System.out.println(mi);
    }

    @Test
    public void selectCondition(){
        ProductInfoVo vo = new ProductInfoVo();
        vo.setTypeid(3);
        vo.setLprice(2000);
        vo.setHprice(3000);
        List<ProductInfo> list = mapper.selectCondition(vo);
        list.forEach(productInfo -> System.out.println(productInfo));
    }

}

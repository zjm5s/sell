package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.ProductInfo;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductInfoServiceImplTest {

    @Autowired
    ProductInfoServiceImpl service;

    @Test
    @Transactional
    public void saveOneTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("一碗粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(10);
        productInfo.setProductIcon("xxx.jpg");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductStatus(0);
        productInfo.setProductId("12345");
        boolean save = service.save(productInfo);
        Assert.assertNotNull(save);
    }

    @Test
    public void selectAllTest(){
        List<ProductInfo> list = service.list();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findUpAllTest(){
        IPage<ProductInfo> upAll = service.findUpAll();
        System.out.println(upAll.getRecords());
        System.out.println(upAll.getPages());
        System.out.println(upAll.getCurrent());
        System.out.println(upAll.getSize());
        System.out.println(upAll.getTotal());
    }
}
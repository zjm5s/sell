package com.imooc.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoMapperTest {
    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Test
    public void selectOneTest(){
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("categoryId",1);
        ProductCategory productCategory = productCategoryMapper.selectOne(queryWrapper);
        System.out.println(productCategory);
    }
}
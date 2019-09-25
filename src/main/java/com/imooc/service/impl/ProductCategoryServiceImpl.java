package com.imooc.service.impl;

import com.imooc.entity.ProductCategory;
import com.imooc.mapper.ProductCategoryMapper;
import com.imooc.service.IProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-09-25
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {
    @Autowired
    ProductCategoryMapper mapper;

    public ProductCategory mySelect() {
        return mapper.mySelect();
    }
}

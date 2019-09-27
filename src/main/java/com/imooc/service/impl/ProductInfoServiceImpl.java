package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.ProductInfo;
import com.imooc.mapper.ProductCategoryMapper;
import com.imooc.mapper.ProductInfoMapper;
import com.imooc.service.IProductInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-09-25
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements IProductInfoService {

    public ProductInfo findOne(Integer id){
        QueryWrapper<ProductInfo> productInfoQueryWrapper = new QueryWrapper<>();
        productInfoQueryWrapper.eq("product_id",id);
        return getOne(productInfoQueryWrapper);
    }

    public IPage<ProductInfo> findUpAll(){
        Page<ProductInfo> productInfoPage = new Page<>();
        return page(productInfoPage);
    }

    public void test(){

    }
}

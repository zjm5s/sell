package com.imooc.service;

import com.imooc.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-09-25
 */
public interface IProductCategoryService extends IService<ProductCategory> {
    List<ProductCategory> findByCategoryTypesIn(List<Integer> list);
}

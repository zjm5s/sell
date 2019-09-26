package com.imooc.mapper;

import com.imooc.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-09-25
 */
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    List<ProductCategory> selectByCategoryTypesIn(List<Integer> list);
}

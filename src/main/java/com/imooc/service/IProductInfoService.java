package com.imooc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.dto.CartDTO;
import com.imooc.entity.ProductInfo;
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
public interface IProductInfoService extends IService<ProductInfo> {
    IPage<ProductInfo> findUpAll();

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}

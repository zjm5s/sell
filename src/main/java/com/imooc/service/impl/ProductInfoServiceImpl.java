package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.dto.CartDTO;
import com.imooc.entity.ProductInfo;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.mapper.ProductCategoryMapper;
import com.imooc.mapper.ProductInfoMapper;
import com.imooc.service.IProductInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-09-25
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements IProductInfoService {

    public IPage<ProductInfo> findUpAll() {
        Page<ProductInfo> productInfoPage = new Page<>();
        return page(productInfoPage);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            QueryWrapper<ProductInfo> productInfoQueryWrapper = new QueryWrapper<>();
            productInfoQueryWrapper.eq("product_id", cartDTO.getProductId());
            ProductInfo productInfo = getOne(productInfoQueryWrapper);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            UpdateWrapper<ProductInfo> productInfoUpdateWrapper = new UpdateWrapper<>();
            productInfoUpdateWrapper.eq("product_id",cartDTO.getProductId());
            update(productInfo, productInfoUpdateWrapper);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            QueryWrapper<ProductInfo> productInfoQueryWrapper = new QueryWrapper<>();
            productInfoQueryWrapper.eq("product_id", cartDTO.getProductId());
            ProductInfo productInfo = getOne(productInfoQueryWrapper);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //减库存
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            UpdateWrapper<ProductInfo> productInfoUpdateWrapper = new UpdateWrapper<>();
            productInfoUpdateWrapper.eq("product_id", productInfo.getProductId());
            update(productInfo, productInfoUpdateWrapper);
        }
    }

    //加库存

    //减库存
}

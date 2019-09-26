package com.imooc.controller;

import com.imooc.VO.ProductInfoVO;
import com.imooc.VO.ProductVO;
import com.imooc.VO.ResultVO;
import com.imooc.entity.ProductCategory;
import com.imooc.entity.ProductInfo;
import com.imooc.service.impl.ProductCategoryServiceImpl;
import com.imooc.service.impl.ProductInfoServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    ProductInfoServiceImpl productInfoService;

    @Autowired
    ProductCategoryServiceImpl productCategoryService;

    /**
     * @Description 查询所有商品信息，不分页
     * @Author 曾锦铭
     * @DateTime 9/26/2019 7:01 PM
     */
    @GetMapping("/list")
    public ResultVO list() {
        List<ProductInfo> productInfos = productInfoService.list();
        List<Integer> categoryTypeList = productInfos
                .stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> categories =
                productCategoryService.findByCategoryTypesIn(categoryTypeList);
        List<ProductVO> productVOS = new ArrayList<>();
        categories.forEach(category -> {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());
            List<ProductInfoVO> productInfoVOS = new ArrayList<>();
            productInfos.forEach(productInfo -> {
                ProductInfoVO productInfoVO = new ProductInfoVO();
                BeanUtils.copyProperties(productInfo,productInfoVO);
                productInfoVOS.add(productInfoVO);
            });
            productVO.setProductInfoVOS(productInfoVOS);
            productVOS.add(productVO);
        });

        ResultVO<List<ProductVO>> productVOResultVO = new ResultVO<>();
        productVOResultVO.setCode(0);
        productVOResultVO.setMsg("成功");
        productVOResultVO.setData(productVOS);
        return productVOResultVO;

    }
}

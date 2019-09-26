package com.imooc.controller;

import com.imooc.VO.ProductInfoVO;
import com.imooc.VO.ProductVO;
import com.imooc.VO.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @GetMapping("/list")
    public ResultVO list(){
        ResultVO productVOResultVO = new ResultVO<>();
        ProductVO productVO = new ProductVO();
        ProductInfoVO productInfoVO = new ProductInfoVO();
        productVOResultVO.setCode(0);
        productVOResultVO.setMsg("成功");
        productVO.setProductInfoVOS(Arrays.asList(productInfoVO));
        productVOResultVO.setData(Arrays.asList(productVO));
        return productVOResultVO;
    }
}

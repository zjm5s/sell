package com.imooc.controller;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.impl.OrderMasterServiceImpl;
import com.imooc.service.impl.PayServiceImpl;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;
    @Autowired
    private PayServiceImpl payService;

    //手快，在租用支付测试账号时填错地址
    @GetMapping("/crate")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
        //查询订单
        OrderDTO orderDTO = orderMasterService.findOne(orderId);
//        orderDTO.setBuyerOpenid("oTgZpwfqB17WfF1yNiHEYIY1BS3g");
        if (orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        PayResponse response = payService.create(orderDTO);
        map.put("payResponse",response);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/crate",map);
    }
}

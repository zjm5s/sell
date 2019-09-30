package com.imooc.service;

import com.imooc.dto.OrderDTO;

public interface BuyerService {
    //查询一个订单
    OrderDTO findOneOrder(String openid,String orderId);

    //取消一个订单
    OrderDTO cancelOrder(String openid,String orderId);
}

package com.imooc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.dto.OrderDTO;
import com.imooc.entity.OrderMaster;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-09-25
 */
public interface IOrderMasterService extends IService<OrderMaster> {
    /** 创建订单 */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单 */
    OrderDTO findOne(String orderId);

    /** 查询订单列表 */
    IPage<OrderDTO> findList(String buyerOpenid,IPage<OrderMaster> orderMasterIPage);

    /** 取消订单 */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单 */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单 */
    OrderDTO pay(OrderDTO orderDTO);
}

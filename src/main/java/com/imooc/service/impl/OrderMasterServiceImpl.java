package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.dto.OrderDTO;
import com.imooc.entity.OrderMaster;
import com.imooc.mapper.OrderDetailMapper;
import com.imooc.mapper.OrderMasterMapper;
import com.imooc.service.IOrderMasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-09-25
 */
@Service
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements IOrderMasterService {

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        return null;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public IPage<OrderDTO> findList(String buyerOpenid) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        return null;
    }
}

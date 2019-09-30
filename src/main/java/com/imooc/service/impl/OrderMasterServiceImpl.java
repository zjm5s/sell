package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.Utils.KeyUtil;
import com.imooc.converter.OrderMaster2OrderDTOConverter;
import com.imooc.dto.CartDTO;
import com.imooc.dto.OrderDTO;
import com.imooc.entity.OrderDetail;
import com.imooc.entity.OrderMaster;
import com.imooc.entity.ProductInfo;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.mapper.OrderDetailMapper;
import com.imooc.mapper.OrderMasterMapper;
import com.imooc.service.IOrderMasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-09-25
 */
@Service
@Slf4j
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements IOrderMasterService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
//        1.查询商品（价格，数量）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            QueryWrapper<ProductInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("product_id", orderDetail.getProductId());
            ProductInfo productInfo = productInfoService.getOne(queryWrapper);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
//            2.计算总价
            orderAmount = productInfo.getProductPrice().multiply(
                    new BigDecimal(orderDetail.getProductQuantity())
            ).add(orderAmount);
//            3.订单详情入库
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetailMapper.insert(orderDetail);
        }
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderAmount(orderAmount);
        save(orderMaster);

//        4.减库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e->
                        new CartDTO(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        QueryWrapper<OrderMaster> orderMasterQueryWrapper = new QueryWrapper<>();
        orderMasterQueryWrapper.eq("order_id",orderId);
        OrderMaster orderMaster = getOne(orderMasterQueryWrapper);
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
        orderDetailQueryWrapper.eq("order_id",orderId);
        List<OrderDetail> orderDetailList = orderDetailMapper.selectList(orderDetailQueryWrapper);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public IPage<OrderDTO> findList(String buyerOpenid,IPage<OrderMaster> orderMasterIPage) {
        QueryWrapper<OrderMaster> orderMasterQueryWrapper = new QueryWrapper<>();
        orderMasterQueryWrapper.eq("buyer_openid",buyerOpenid);
        IPage<OrderMaster> page = page(orderMasterIPage, orderMasterQueryWrapper);
        Page<OrderDTO> orderDTOPage = new Page<>();
        BeanUtils.copyProperties(orderMasterIPage,orderDTOPage);
        orderDTOPage.setRecords(OrderMaster2OrderDTOConverter.convert(orderMasterIPage.getRecords()));
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单不正确，orderId={}，orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        UpdateWrapper<OrderMaster> orderMasterUpdateWrapper = new UpdateWrapper<>();
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderMasterUpdateWrapper.eq("order_id",orderDTO.getOrderId());
        boolean update = update(orderMaster,orderMasterUpdateWrapper);
        if (!update){
            log.error("【取消订单】更新失败，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            /**
            *9/29/2019 10:25 PM  TODO: 退款操作
            */
        }

        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        UpdateWrapper<OrderMaster> orderMasterUpdateWrapper = new UpdateWrapper<>();
        orderMasterUpdateWrapper.eq("order_id",orderMaster.getOrderId());
        boolean update = update(orderMaster, orderMasterUpdateWrapper);
        if (!update){
            log.error("【完结订单】更新订单失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】支付状态不正确，orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        UpdateWrapper<OrderMaster> orderMasterUpdateWrapper = new UpdateWrapper<>();
        orderMasterUpdateWrapper.eq("order_id",orderMaster.getOrderId());
        boolean update = update(orderMaster, orderMasterUpdateWrapper);
        if (!update){
            log.error("【支付订单】支付订单失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}

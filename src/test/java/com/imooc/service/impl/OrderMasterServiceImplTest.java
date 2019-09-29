package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.dto.OrderDTO;
import com.imooc.entity.OrderDetail;
import com.imooc.entity.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterServiceImplTest {

    @Autowired
    OrderMasterServiceImpl orderMasterService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid("123456");
        orderDTO.setBuyerAddress("南京");
        orderDTO.setBuyerName("司机名字");
        orderDTO.setBuyerPhone("1311111111");
        List<OrderDetail> orderDetailList = new ArrayList<>();
        //购买一碗瘦肉粥
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123456");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);
        //购买两碗银耳粥
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("12347");
        orderDetail1.setProductQuantity(2);
        orderDetailList.add(orderDetail1);
        orderDTO.setOrderDetailList(orderDetailList);
        orderMasterService.create(orderDTO);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderMasterService.findOne("1569741285757288168");
        System.out.println(orderDTO);
    }

    @Test
    public void findList() {
        IPage<OrderDTO> list = orderMasterService.findList("123455",new Page<OrderMaster>(1,10));
        List<OrderDTO> records = list.getRecords();
        long current = list.getCurrent();
        long pages = list.getPages();
        long size = list.getSize();
        long total = list.getTotal();
        System.out.println(records);
        System.out.println(current);
        System.out.println(pages);
        System.out.println(size);
        System.out.println(pages);
        System.out.println(total);
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void pay() {
    }
}
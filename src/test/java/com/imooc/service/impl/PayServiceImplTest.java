package com.imooc.service.impl;

import com.imooc.dto.OrderDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {
    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    @Test
    public void create() {
        System.out.println(payService);
        OrderDTO orderDTO = orderMasterService.findOne("1569741141753243683");
        orderDTO.setBuyerOpenid("oTgZpwfqB17WfF1yNiHEYIY1BS3g");
        payService.create(orderDTO);
    }
}
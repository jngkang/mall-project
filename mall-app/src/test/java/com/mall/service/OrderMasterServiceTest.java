package com.mall.service;

import com.mall.query.OrderMasterQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class OrderMasterServiceTest {

    @Resource
    private OrderMasterService orderMasterService;

    @Test
    void getPage() {
        System.out.println(orderMasterService.getPage(new OrderMasterQuery()));
    }
}
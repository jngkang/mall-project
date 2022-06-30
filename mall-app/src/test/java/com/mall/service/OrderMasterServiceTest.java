package com.mall.service;

import com.mall.model.query.OrderMasterQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderMasterServiceTest {

    @Resource
    private OrderMasterService orderMasterService;

    @Test
    void getPage() {
        System.out.println(orderMasterService.getPage(new OrderMasterQuery()));
    }
}
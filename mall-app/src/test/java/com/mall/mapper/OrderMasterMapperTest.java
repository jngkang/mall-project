package com.mall.mapper;

import com.mall.query.OrderMasterQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class OrderMasterMapperTest {

    @Resource
    private OrderMasterMapper orderMasterMapper;

    @Test
    void select() {
        System.out.println(orderMasterMapper.select(new OrderMasterQuery()));
    }
}
package com.mall.mapper;

import com.mall.model.query.OrderMasterQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderMasterMapperTest {

    @Resource
    private OrderMasterMapper orderMasterMapper;

    @Test
    void select() {
        System.out.println(orderMasterMapper.select(new OrderMasterQuery()));
    }
}
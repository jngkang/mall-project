package com.mall.dao;

import com.mall.query.OrderMasterQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class OrderMasterDaoTest {

    @Resource
    private OrderMasterDao orderMasterDao;

    @Test
    void select() {
        System.out.println(orderMasterDao.select(new OrderMasterQuery()));
    }
}
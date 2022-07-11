package com.mall.service;

import com.mall.dao.ProductDao;
import com.mall.query.ProductQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ProductServiceTest {

    @Resource
    private ProductDao productDao;

    @Test
    void select() {
        System.out.println(productDao.select(new ProductQuery()));
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void updateStatus() {
    }
}
package com.mall.dao;

import com.mall.query.ProductQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ProductDaoTest {

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
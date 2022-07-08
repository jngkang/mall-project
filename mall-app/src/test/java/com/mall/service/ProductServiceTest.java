package com.mall.service;

import com.mall.mapper.ProductMapper;
import com.mall.query.ProductQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ProductServiceTest {

    @Resource
    private ProductMapper productMapper;

    @Test
    void select() {
        System.out.println(productMapper.select(new ProductQuery()));
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
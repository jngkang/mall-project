package com.mall.service;

import com.mall.mapper.ProductMapper;
import com.mall.model.query.ProductQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

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
package com.mall.mapper;

import com.mall.query.ProductQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ProductMapperTest {

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
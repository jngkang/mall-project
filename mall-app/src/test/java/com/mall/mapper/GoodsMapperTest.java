package com.mall.mapper;

import com.mall.model.query.GoodsQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoodsMapperTest {

    @Resource
    private GoodsMapper goodsMapper;

    @Test
    void select() {
        System.out.println(goodsMapper.select(new GoodsQuery()));
    }
}
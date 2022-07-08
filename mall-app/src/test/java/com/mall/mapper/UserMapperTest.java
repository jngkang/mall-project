package com.mall.mapper;

import com.mall.query.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void select() {
        System.out.println(userMapper.select(new UserQuery()));
    }
}
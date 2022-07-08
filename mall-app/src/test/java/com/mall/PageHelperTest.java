package com.mall;

import com.mall.mapper.UserMapper;
import com.mall.query.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author JngKang
 * @date 2022-06-29 08:53
 */
@SpringBootTest
public class PageHelperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void fun() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setPageNum(1);
        userQuery.setPageSize(1);
        System.out.println(userMapper.select(userQuery));
    }
}

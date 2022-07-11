package com.mall.dao;

import com.mall.query.UserQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserDaoTest {

    @Resource
    private UserDao userDao;

    @Test
    void select() {
        System.out.println(userDao.select(new UserQuery()));
    }
}
package com.mall.dao;

import com.mall.entity.BillIn;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.time.LocalDateTime;

@SpringBootTest
class BillInDaoTest {

    @Resource
    private BillInDao billInDao;

    @Test
    void insert() {
        BillIn billIn = BillIn.builder()
                .billNo("1")
                .vendorId(1)
                .billDate(LocalDateTime.now())
                .lastUpdateBy("1")
                .lastUpdateTime(LocalDateTime.now())
                .build();
        System.out.println(billInDao.insert(billIn));
    }
}
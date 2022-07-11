package com.mall.dao;

import com.mall.entity.BillMaster;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.time.LocalDateTime;

@SpringBootTest
class BillMasterDaoTest {

    @Resource
    private BillMasterDao billMasterDao;

    @Test
    void insert() {
        BillMaster billMaster = BillMaster.builder()
                .billNo("1")
                .vendorId(1)
                .billDate(LocalDateTime.now())
                .lastUpdateBy("1")
                .lastUpdateTime(LocalDateTime.now())
                .build();
        System.out.println(billMasterDao.insert(billMaster));
    }
}
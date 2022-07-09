package com.mall.mapper;

import com.mall.entity.BillMaster;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BillMasterMapperTest {

    @Resource
    private BillMasterMapper billMasterMapper;

    @Test
    void insert() {
        BillMaster billMaster = BillMaster.builder()
                .billNo("1")
                .vendorId(1)
                .billDate(LocalDateTime.now())
                .lastUpdateBy("1")
                .lastUpdateTime(LocalDateTime.now())
                .build();
        System.out.println(billMasterMapper.insert(billMaster));
    }
}
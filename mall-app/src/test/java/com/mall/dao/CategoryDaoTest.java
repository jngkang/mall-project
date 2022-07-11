package com.mall.dao;

import com.mall.status.CategoryStatusUpdater;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CategoryDaoTest {

    @Resource
    private CategoryDao categoryDao;

    @Test
    void update() {
        CategoryStatusUpdater category = CategoryStatusUpdater.builder()
                .id(1L)
                .status(3)
                .build();
        categoryDao.updateStatus(category);
    }
}
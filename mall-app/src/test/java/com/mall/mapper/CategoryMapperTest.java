package com.mall.mapper;

import com.mall.model.status.CategoryStatusUpdater;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CategoryMapperTest {

    @Resource
    private CategoryMapper categoryMapper;

    @Test
    void update() {
        CategoryStatusUpdater category = CategoryStatusUpdater.builder()
                .id(1L)
                .status(3)
                .build();
        categoryMapper.updateStatus(category);
    }
}
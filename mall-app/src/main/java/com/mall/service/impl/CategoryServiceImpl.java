package com.mall.service.impl;

import com.mall.mapper.CategoryMapper;
import com.mall.model.Category;
import com.mall.model.query.CategoryQuery;
import com.mall.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> select(CategoryQuery categoryQuery) {
        return categoryMapper.select(categoryQuery);
    }

    @Override
    public Integer insert(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public Integer update(Category category) {
        return categoryMapper.update(category);
    }
}

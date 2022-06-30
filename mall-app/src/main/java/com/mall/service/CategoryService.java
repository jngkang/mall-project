package com.mall.service;

import com.mall.model.Category;
import com.mall.model.query.CategoryQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public interface CategoryService {

    public List<Category> select(CategoryQuery categoryQuery);

    public Integer insert(Category category);

    public Integer update(Category category);

}

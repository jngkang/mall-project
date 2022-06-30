package com.mall.controller;

import com.mall.model.Category;
import com.mall.model.query.CategoryQuery;
import com.mall.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-30 08:49
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/page")
    public List<Category> select(@RequestBody CategoryQuery categoryQuery) {
        return categoryService.select(categoryQuery);
    }

    @PostMapping("/add")
    public Integer insert(@RequestBody Category category) {
        return categoryService.insert(category);
    }

    @PostMapping("/update")
    public Integer update(@RequestBody Category category) {
        return categoryService.update(category);
    }
}

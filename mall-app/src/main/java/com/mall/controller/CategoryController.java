package com.mall.controller;

import com.mall.model.Category;
import com.mall.model.dto.CategoryDTO;
import com.mall.model.query.CategoryQuery;
import com.mall.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
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

    @PostMapping("/page")
    public List<Category> select(@RequestBody CategoryQuery categoryQuery) {
        return categoryService.select(categoryQuery);
    }

    @GetMapping("/pid/{pid}")
    public List<Category> selectByPid(@PathVariable Long pid) {
        return select(CategoryQuery.builder().pid(pid).build());
    }

    @PostMapping("/add")
    public Integer insert(@RequestBody CategoryDTO categoryDTO) throws IOException {
        return categoryService.insert(categoryDTO);
    }

    @PostMapping("/update")
    public Integer update(@RequestBody Category category) {
        return categoryService.update(category);
    }
}

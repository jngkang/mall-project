package com.mall.controller;

import com.mall.annotation.PageX;
import com.mall.enums.ProductStatus;
import com.mall.model.dto.ProductDTO;
import com.mall.model.query.CategoryQuery;
import com.mall.model.query.ProductQuery;
import com.mall.model.status.ProductStatusUpdater;
import com.mall.service.CategoryService;
import com.mall.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-07-02 10:31
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private CategoryService categoryService;

    @PageX
    @PostMapping("/page")
    public List select(@RequestBody ProductQuery query) {
        // 创建一个集合
        List<ProductDTO> productList = productService.select(query);
        for (ProductDTO item : productList) {
            String categoryName = categoryService.select(CategoryQuery.builder().id(item.getCategoryId()).build()).get(0).getName();
            item.setCategoryName(categoryName);
            item.setStatusX(ProductStatus.findByCode(item.getStatus()).getName());
        }
        return productList;
    }

    @PostMapping("/add")
    public String insert(@RequestBody ProductDTO productDTO) {
        return productService.insert(productDTO);
    }

    @PostMapping("/status/update")
    public String updateStatus(@RequestBody ProductStatusUpdater productStatusUpdater) {
        return productService.updateStatus(productStatusUpdater);
    }

}

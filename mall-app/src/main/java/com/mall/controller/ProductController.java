package com.mall.controller;

import com.mall.annotation.NoAuthorization;
import com.mall.entity.Product;
import com.mall.model.ProductDTO;
import com.mall.query.ProductQuery;
import com.mall.service.CategoryService;
import com.mall.service.ProductService;
import com.mall.status.ProductStatusUpdater;
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

    @PostMapping("/page")
    @NoAuthorization
    public List select(@RequestBody ProductQuery query) {
        return productService.select(query);
    }

    @PostMapping("/add")
    public String insert(@RequestBody ProductDTO productDTO) {
        return productService.insert(productDTO);
    }

    @PostMapping("/update")
    public String updateStatus(@RequestBody Product product) {
        return productService.update(product);
    }

    @PostMapping("/status/update")
    public String updateStatus(@RequestBody ProductStatusUpdater productStatusUpdater) {
        return productService.updateStatus(productStatusUpdater);
    }

}

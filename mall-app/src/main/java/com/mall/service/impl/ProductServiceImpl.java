package com.mall.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mall.entity.Product;
import com.mall.enums.ProductStatus;
import com.mall.mapper.ProductMapper;
import com.mall.model.ProductDTO;
import com.mall.query.ProductQuery;
import com.mall.service.ProductService;
import com.mall.status.ProductStatusUpdater;
import com.mall.threadlocal.CurrentThreadLocal;
import com.mall.util.UploadUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> select(ProductQuery productQuery) {
        List<ProductDTO> res = new ArrayList<>();
        List<Product> products = productMapper.select(productQuery);
        for (Product product : products) {
            ProductDTO temp = new ProductDTO();
            temp.setId(product.getId());
            temp.setName(product.getName());
            temp.setSeq(product.getSeq());
            temp.setStatus(product.getStatus());
            temp.setStatusX(ProductStatus.findByCode(product.getStatus()).getName());
            temp.setLastUpdateBy(product.getLastUpdateBy());
            temp.setLastUpdateTime(product.getLastUpdateTime());
            temp.setCategoryId(product.getCategoryId());
            temp.setBrief(product.getBrief());
            temp.setImg(product.getImg());
            temp.setPrice(product.getPrice());
            res.add(temp);
        }
        return res;
    }

    @Override
    public String insert(ProductDTO productDTO) {
        if (ObjectUtil.isEmpty(productDTO.getStatus())) {
            productDTO.setStatus(1);
        }
        Product product = Product.builder()
                .categoryId(productDTO.getCategoryId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .brief(productDTO.getBrief())
                .seq(productDTO.getSeq())
                .status(productDTO.getStatus())
                .img(UploadUtil.uploadImg(productDTO.getImg(), productDTO.getImgName()))
                .lastUpdateBy(CurrentThreadLocal.get().getUsername())
                .lastUpdateTime(LocalDateTime.now())
                .build();
        Integer res = productMapper.insert(product);
        return res > 0 ? "ok" : "err";
    }

    @Override
    public String update(Product product) {
        if (ObjectUtil.isEmpty(product.getStatus())) {
            product.setStatus(1);
        }
        product.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
        product.setLastUpdateTime(LocalDateTime.now());
        return productMapper.update(product) > 0 ? "ok" : "err";
    }

    @Override
    public String updateStatus(ProductStatusUpdater productStatusUpdater) {
        return productMapper.updateStatus(productStatusUpdater) > 0 ? "ok" : "err";
    }
}

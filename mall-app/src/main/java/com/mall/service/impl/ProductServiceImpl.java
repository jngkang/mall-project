package com.mall.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mall.mapper.ProductMapper;
import com.mall.model.Product;
import com.mall.model.dto.ProductDTO;
import com.mall.model.query.ProductQuery;
import com.mall.model.status.ProductStatusUpdater;
import com.mall.service.ProductService;
import com.mall.threadlocal.CurrentThreadLocal;
import com.mall.util.UploadUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
        return productMapper.select(productQuery);
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

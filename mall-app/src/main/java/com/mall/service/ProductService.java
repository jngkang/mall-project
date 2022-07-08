package com.mall.service;

import com.mall.entity.Product;
import com.mall.model.ProductDTO;
import com.mall.query.ProductQuery;
import com.mall.status.ProductStatusUpdater;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public interface ProductService {

    public List<ProductDTO> select(ProductQuery productQuery);

    public String insert(ProductDTO productDTO);

    public String update(Product product);

    public String updateStatus(ProductStatusUpdater productStatusUpdater);

}

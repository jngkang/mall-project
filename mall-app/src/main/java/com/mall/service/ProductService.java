package com.mall.service;

import com.mall.model.Product;
import com.mall.model.dto.ProductDTO;
import com.mall.model.query.ProductQuery;
import com.mall.model.status.ProductStatusUpdater;
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

    public Integer update(Product product);

    public String updateStatus(ProductStatusUpdater productStatusUpdater);

}

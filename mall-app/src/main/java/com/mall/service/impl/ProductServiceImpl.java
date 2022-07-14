package com.mall.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mall.converter.ProductMapper;
import com.mall.dao.ProductDao;
import com.mall.entity.Product;
import com.mall.enums.ProductStatus;
import com.mall.globel.Const;
import com.mall.model.ProductDTO;
import com.mall.query.ProductQuery;
import com.mall.service.ProductService;
import com.mall.status.ProductStatusUpdater;
import com.mall.threadlocal.CurrentThreadLocal;
import com.mall.util.UploadUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDao productDao;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public List<ProductDTO> select(ProductQuery productQuery) {
        List<ProductDTO> res = new ArrayList<>();
        List<Product> products = productDao.select(productQuery);
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
    public List<ProductDTO> selectByRedis(ProductQuery productQuery) {
        List<ProductDTO> productDTOList = null;
        Set<String> keys = redisTemplate.keys(Const.PRODUCT_PROXY + "*");
        if (ObjectUtil.isNotEmpty(keys)) {
            Long[] ids = new Long[keys.size()];
            int i = 0;
            for (String key : keys) {
                Long id = Long.parseLong(key.substring(Const.PRODUCT_PROXY.length()));
                ids[i++] = id;
            }
            productQuery.setIds(ids);

            List<Product> productList = productDao.select(productQuery);
            productDTOList = ProductMapper.MAPPER.beanList2DTOList(productList);
            for (ProductDTO productDTO : productDTOList) {
                Integer qty = (Integer) redisTemplate.boundValueOps(Const.PRODUCT_PROXY + productDTO.getId()).get();
                productDTO.setQty(qty);
            }
        }
        return productDTOList;
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
        Integer res = productDao.insert(product);
        return res > 0 ? "ok" : "err";
    }

    @Override
    public String update(Product product) {
        if (ObjectUtil.isEmpty(product.getStatus())) {
            product.setStatus(1);
        }
        product.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
        product.setLastUpdateTime(LocalDateTime.now());
        return productDao.update(product) > 0 ? "ok" : "err";
    }

    @Override
    public String updateStatus(ProductStatusUpdater productStatusUpdater) {
        return productDao.updateStatus(productStatusUpdater) > 0 ? "ok" : "err";
    }
}

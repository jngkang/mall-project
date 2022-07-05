package com.mall.mapper;

import com.mall.annotation.PageX;
import com.mall.model.Product;
import com.mall.model.dto.ProductDTO;
import com.mall.model.query.ProductQuery;
import com.mall.model.status.ProductStatusUpdater;
import com.mall.utils.SqlGen;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-10 14:46
 */
@Mapper
public interface ProductMapper {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<ProductDTO> select(ProductQuery productQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(Product product);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer update(Product product);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer updateStatus(ProductStatusUpdater productStatusUpdater);

}
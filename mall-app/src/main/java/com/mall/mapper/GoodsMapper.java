package com.mall.mapper;

import com.mall.annotation.PageX;
import com.mall.model.Goods;
import com.mall.model.query.GoodsQuery;
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
public interface GoodsMapper {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<Goods> select(GoodsQuery goodsQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(Goods goods);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer update(Goods goods);

}
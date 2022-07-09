package com.mall.mapper;

import com.mall.annotation.PageX;
import com.mall.entity.BillItem;
import com.mall.query.BillItemQuery;
import com.mall.utils.SqlGen;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface BillItemMapper {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<BillItem> select(BillItemQuery billItemQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(BillItem billItem);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer update(BillItem billItem);

}
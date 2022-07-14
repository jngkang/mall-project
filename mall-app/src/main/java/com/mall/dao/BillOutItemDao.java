package com.mall.dao;

import com.mall.annotation.PageX;
import com.mall.entity.BillOutItem;
import com.mall.query.BillOutItemQuery;
import com.mall.utils.SqlGen;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface BillOutItemDao {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<BillOutItem> select(BillOutItemQuery billOutItemQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(BillOutItem billOutItem);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer update(BillOutItem billOutItem);

}
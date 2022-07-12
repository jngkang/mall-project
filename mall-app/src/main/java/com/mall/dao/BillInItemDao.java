package com.mall.dao;

import com.mall.annotation.PageX;
import com.mall.entity.BillInItem;
import com.mall.query.BillInItemQuery;
import com.mall.utils.SqlGen;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface BillInItemDao {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<BillInItem> select(BillInItemQuery billInItemQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(BillInItem billInItem);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer update(BillInItem billInItem);

}
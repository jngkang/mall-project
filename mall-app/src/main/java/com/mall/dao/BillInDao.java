package com.mall.dao;

import com.mall.annotation.PageX;
import com.mall.entity.BillIn;
import com.mall.query.BillInQuery;
import com.mall.utils.SqlGen;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface BillInDao {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<BillIn> select(BillInQuery billInQuery);

    @SelectKey(keyColumn = "id", keyProperty = "id", statement = "select last_insert_id()", before = false, resultType = Integer.class)
    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(BillIn billIn);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer update(BillIn billIn);

}
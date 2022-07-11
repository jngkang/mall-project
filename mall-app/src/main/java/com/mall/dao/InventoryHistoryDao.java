package com.mall.dao;

import com.mall.annotation.PageX;
import com.mall.entity.InventoryHistory;
import com.mall.query.InventoryHistoryQuery;
import com.mall.utils.SqlGen;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface InventoryHistoryDao {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<InventoryHistory> select(InventoryHistoryQuery inventoryHistoryQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(InventoryHistory inventoryHistory);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer update(InventoryHistory inventoryHistory);

}
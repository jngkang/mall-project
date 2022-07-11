package com.mall.dao;

import com.mall.annotation.PageX;
import com.mall.entity.Inventory;
import com.mall.query.InventoryQuery;
import com.mall.utils.SqlGen;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InventoryDao {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<Inventory> select(InventoryQuery inventoryQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(Inventory inventory);

    @Update("update inventory " +
            "set productId = ${productId}, " +
            "accountQty = ${accountQty}, " +
            "qty = ${qty}, " +
            "soldQty = ${soldQty}, " +
            "lastUpdateBy = '${lastUpdateBy}', " +
            "lastUpdateTime = '${lastUpdateTime}', " +
            "version = ${version} + 1 " +
            "where id = ${id} and version = ${version}")
    public Integer update(Inventory inventory);

}
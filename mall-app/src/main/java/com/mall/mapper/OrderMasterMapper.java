package com.mall.mapper;

import com.mall.annotation.PageX;
import com.mall.entity.OrderMaster;
import com.mall.query.OrderMasterQuery;
import com.mall.utils.SqlGen;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-10 14:46
 */
@Mapper
public interface OrderMasterMapper {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<OrderMaster> select(OrderMasterQuery orderMasterQuery);

    //@InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    //public Integer insert(User user);

    //@UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    //public Integer update(User user);

    //@UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    //public Integer updateStatus(OrderMasterStatus orderMasterStatus);
}
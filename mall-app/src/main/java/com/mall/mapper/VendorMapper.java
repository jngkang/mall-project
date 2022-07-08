package com.mall.mapper;

import com.mall.annotation.PageX;
import com.mall.entity.Vendor;
import com.mall.query.VendorQuery;
import com.mall.status.VendorStatusUpdater;
import com.mall.utils.SqlGen;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface VendorMapper {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<Vendor> select(VendorQuery vendorQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(Vendor vendor);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer update(Vendor vendor);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer updateStatus(VendorStatusUpdater vendorStatusUpdater);

}
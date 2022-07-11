package com.mall.dao;

import com.mall.annotation.PageX;
import com.mall.entity.Category;
import com.mall.query.CategoryQuery;
import com.mall.status.CategoryStatusUpdater;
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
public interface CategoryDao {

    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<Category> select(CategoryQuery categoryQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(Category category);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer update(Category category);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer updateStatus(CategoryStatusUpdater categoryStatusUpdater);
}
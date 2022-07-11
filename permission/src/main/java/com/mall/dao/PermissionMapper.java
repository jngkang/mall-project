package com.mall.dao;

import com.mall.annotation.PageX;
import com.mall.model.Permission;
import com.mall.model.PermissionDTO;
import com.mall.model.PermissionQuery;
import com.mall.model.PermissionStatus;
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
public interface PermissionMapper {

    /**
     * 登录
     *
     * @param userQuery 根据用户id查询
     * @return java.util.List<com.mall.model.VO.UserVO> 返回用户的信息
     */
    @PageX
    @SelectProvider(type = SqlGen.class, method = SqlGen.SELECT)
    public List<PermissionDTO> select(PermissionQuery permissionQuery);

    @InsertProvider(type = SqlGen.class, method = SqlGen.INSERT)
    public Integer insert(Permission permission);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer update(Permission permission);

    @UpdateProvider(type = SqlGen.class, method = SqlGen.UPDATE)
    public Integer updateStatus(PermissionStatus permissionStatus);
}
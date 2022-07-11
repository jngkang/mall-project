package com.mall.service.impl;

import com.mall.dao.PermissionMapper;
import com.mall.model.PermissionDTO;
import com.mall.model.PermissionQuery;
import com.mall.model.PermissionStatus;
import com.mall.service.PermissionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-07-04 19:58
 */
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionDTO> select(PermissionQuery permissionQuery) {
        return permissionMapper.select(permissionQuery);
    }

    @Override
    public Integer insert(PermissionDTO permissionDTO) {
        return null;
    }

    @Override
    public Integer update(PermissionDTO permissionDTO) {
        return null;
    }

    @Override
    public Integer updateStatus(PermissionStatus permissionStatus) {
        return permissionMapper.updateStatus(permissionStatus);
    }
}

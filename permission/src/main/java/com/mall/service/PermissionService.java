package com.mall.service;

import com.mall.model.PermissionDTO;
import com.mall.model.PermissionQuery;
import com.mall.model.PermissionStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public interface PermissionService {

    public List<PermissionDTO> select(PermissionQuery permissionQuery);

    public Integer insert(PermissionDTO permissionDTO);

    public Integer update(PermissionDTO permissionDTO);

    public Integer updateStatus(PermissionStatus permissionStatus);

}

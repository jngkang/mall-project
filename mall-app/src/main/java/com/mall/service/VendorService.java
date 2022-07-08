package com.mall.service;

import cn.hutool.core.util.ObjectUtil;
import com.mall.entity.Vendor;
import com.mall.enums.VendorStatus;
import com.mall.mapper.VendorMapper;
import com.mall.model.VendorDTO;
import com.mall.query.VendorQuery;
import com.mall.query.VendorQueryDTO;
import com.mall.status.VendorStatusUpdater;
import com.mall.threadlocal.CurrentThreadLocal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendorService {

    @Resource
    private VendorMapper vendorMapper;

    public List<VendorDTO> select(VendorQueryDTO vendorQueryDTO) {
        List<VendorDTO> res = new ArrayList<>();

        VendorQuery vendorQuery = new VendorQuery();
        vendorQuery.setId(vendorQueryDTO.getId());
        vendorQuery.setName(vendorQueryDTO.getName());
        vendorQuery.setStatus(vendorQueryDTO.getStatus());
        vendorQuery.setOrderByBlock(vendorQueryDTO.getOrderByBlock());
        vendorQuery.setPageNum(vendorQueryDTO.getPageNum());
        vendorQuery.setPageSize(vendorQueryDTO.getStatus());

        List<Vendor> vendors = vendorMapper.select(vendorQuery);
        for (Vendor vendor : vendors) {
            VendorDTO vendorDTO = new VendorDTO();
            vendorDTO.setStatus(vendor.getStatus());
            vendorDTO.setStatusX(VendorStatus.findByCode(vendor.getStatus()).getName());
            vendorDTO.setLastUpdateBy(vendor.getLastUpdateBy());
            vendorDTO.setLastUpdateTime(vendor.getLastUpdateTime());
            vendorDTO.setId(vendor.getId());
            vendorDTO.setName(vendor.getName());
            vendorDTO.setProvince(vendor.getProvince());
            vendorDTO.setCity(vendor.getCity());
            vendorDTO.setDistrict(vendor.getDistrict());
            vendorDTO.setAddress(vendor.getAddress());
            vendorDTO.setSeq(vendor.getSeq());
            res.add(vendorDTO);
        }
        return res;
    }

    public Integer insert(VendorDTO vendorDTO) {
        if (ObjectUtil.isEmpty(vendorDTO.getStatus())) {
            vendorDTO.setStatus(1);
        }
        Vendor vendor = new Vendor();
        vendor.setStatus(vendorDTO.getStatus());
        vendor.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
        vendor.setLastUpdateTime(LocalDateTime.now());
        vendor.setId(vendorDTO.getId());
        vendor.setName(vendorDTO.getName());
        vendor.setProvince(vendorDTO.getProvince());
        vendor.setCity(vendorDTO.getCity());
        vendor.setDistrict(vendorDTO.getDistrict());
        vendor.setAddress(vendorDTO.getAddress());
        vendor.setSeq(vendorDTO.getSeq());
        return vendorMapper.insert(vendor);
    }

    public Integer update(VendorDTO vendorDTO) {
        if (ObjectUtil.isEmpty(vendorDTO.getStatus())) {
            vendorDTO.setStatus(1);
        }
        Vendor vendor = new Vendor();
        vendor.setStatus(vendorDTO.getStatus());
        vendor.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
        vendor.setLastUpdateTime(LocalDateTime.now());
        vendor.setId(vendorDTO.getId());
        vendor.setName(vendorDTO.getName());
        vendor.setProvince(vendorDTO.getProvince());
        vendor.setCity(vendorDTO.getCity());
        vendor.setDistrict(vendorDTO.getDistrict());
        vendor.setAddress(vendorDTO.getAddress());
        vendor.setSeq(vendorDTO.getSeq());
        return vendorMapper.update(vendor);
    }

    public Integer updateStatus(VendorStatusUpdater vendorStatusUpdater) {
        return vendorMapper.updateStatus(vendorStatusUpdater);
    }

}
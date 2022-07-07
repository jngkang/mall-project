package com.mall.controller;

import com.mall.annotation.NoAuthorization;
import com.mall.model.dto.VendorDTO;
import com.mall.model.query.VendorQuery;
import com.mall.model.status.VendorStatusUpdater;
import com.mall.service.VendorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Resource
    private VendorService vendorService;

    @NoAuthorization
    @PostMapping("/page")
    public List select(@RequestBody VendorQuery vendorQuery) {
        return vendorService.select(vendorQuery);
    }

    @NoAuthorization
    @PostMapping("/add")
    public Integer insert(@RequestBody VendorDTO vendorDTO) {
        return vendorService.insert(vendorDTO);
    }

    @NoAuthorization
    @PostMapping("/update")
    public Integer update(@RequestBody VendorDTO vendorDTO) {
        return vendorService.update(vendorDTO);
    }

    @NoAuthorization
    @PostMapping("/status/update")
    public Integer updateStatus(@RequestBody VendorStatusUpdater vendorStatusUpdater) {
        return vendorService.updateStatus(vendorStatusUpdater);
    }

}
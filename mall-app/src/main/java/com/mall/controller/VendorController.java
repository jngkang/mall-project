package com.mall.controller;

import com.mall.annotation.NoAuthorization;
import com.mall.model.VendorDTO;
import com.mall.query.VendorQueryDTO;
import com.mall.service.VendorService;
import com.mall.status.VendorStatusUpdater;
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
    @PostMapping("/select")
    public List select(@RequestBody VendorQueryDTO vendorQueryDTO) {
        return vendorService.select(vendorQueryDTO);
    }

    @NoAuthorization
    @PostMapping("/insert")
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
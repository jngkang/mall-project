package com.mall.controller;

import com.mall.model.BillMasterDTO;
import com.mall.query.BillMasterQueryDTO;
import com.mall.service.BillMasterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/billMaster")
public class BillMasterController {

    @Resource
    private BillMasterService billMasterService;

    @PostMapping("/select")
    public List select(@RequestBody BillMasterQueryDTO billMasterQueryDTO) {
        return billMasterService.select(billMasterQueryDTO);
    }

    @PostMapping("/insert")
    public Integer insert(@RequestBody BillMasterDTO billMasterDTO) {
        return billMasterService.insert(billMasterDTO);
    }

    //@PostMapping("/update")
    //public Integer update(@RequestBody BillMasterDTO billMasterDTO) {
    //    return billMasterService.update(billMasterDTO);
    //}

}
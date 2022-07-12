package com.mall.controller;

import com.mall.model.BillInDTO;
import com.mall.query.BillInQueryDTO;
import com.mall.service.BillInService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/billIn")
public class BillInController {

    @Resource
    private BillInService billInService;

    @PostMapping("/select")
    public List select(@RequestBody BillInQueryDTO billInQueryDTO) {
        return billInService.select(billInQueryDTO);
    }

    @PostMapping("/insert")
    public Integer insert(@RequestBody BillInDTO billInDTO) throws Exception {
        return billInService.insert(billInDTO);
    }

    //@PostMapping("/update")
    //public Integer update(@RequestBody BillMasterDTO billMasterDTO) {
    //    return billMasterService.update(billMasterDTO);
    //}

}
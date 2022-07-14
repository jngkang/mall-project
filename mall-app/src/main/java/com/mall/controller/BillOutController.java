package com.mall.controller;

import com.mall.model.BillOutDTO;
import com.mall.query.BillOutQueryDTO;
import com.mall.service.BillOutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/billOut")
public class BillOutController {

    @Resource
    private BillOutService billOutService;

    @PostMapping("/select")
    public List select(@RequestBody BillOutQueryDTO billOutQueryDTO) {
        return billOutService.select(billOutQueryDTO);
    }

    @PostMapping("/insert")
    public Integer insert(@RequestBody BillOutDTO billOutDTO) throws Exception {
        return billOutService.insert(billOutDTO);
    }

    //@PostMapping("/update")
    //public Integer update(@RequestBody BillOutDTO billOutDTO) {
    //    return billOutService.update(billOutDTO);
    //}
    //
    //@PostMapping("/status/update")
    //public Integer updateStatus(@RequestBody BillOutStatusUpdater billOutStatusUpdater) {
    //    return billOutService.updateStatus(billOutStatusUpdater);
    //}

}
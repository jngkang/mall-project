package com.mall.controller;

import com.mall.query.BillOutItemQueryDTO;
import com.mall.service.BillOutItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/billOutItem")
public class BillOutItemController {

    @Resource
    private BillOutItemService billOutItemService;

    @PostMapping("/select")
    public List select(@RequestBody BillOutItemQueryDTO billOutItemQueryDTO) {
        return billOutItemService.select(billOutItemQueryDTO);
    }

    //@PostMapping("/insert")
    //public Integer insert(@RequestBody BillOutItemDTO billOutItemDTO) {
    //    return billOutItemService.insert(billOutItemDTO);
    //}
    //
    //@PostMapping("/update")
    //public Integer update(@RequestBody BillOutItemDTO billOutItemDTO) {
    //    return billOutItemService.update(billOutItemDTO);
    //}
    //
    //@PostMapping("/status/update")
    //public Integer updateStatus(@RequestBody BillOutItemStatusUpdater billOutItemStatusUpdater) {
    //    return billOutItemService.updateStatus(billOutItemStatusUpdater);
    //}

}
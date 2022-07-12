package com.mall.controller;

import com.mall.annotation.NoAuthorization;
import com.mall.query.BillInItemQueryDTO;
import com.mall.service.BillInItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/billInItem")
public class BillInItemController {

    @Resource
    private BillInItemService billInItemService;

    @PostMapping("/select")
    @NoAuthorization
    public List select(@RequestBody BillInItemQueryDTO billInItemQueryDTO) {
        return billInItemService.select(billInItemQueryDTO);
    }

    //@PostMapping("/insert")
    //public Integer insert(@RequestBody BillInItemDTO billInItemDTO) {
    //    return billInItemService.insert(billInItemDTO);
    //}
    //
    //@PostMapping("/update")
    //public Integer update(@RequestBody BillInItemDTO billInItemDTO) {
    //    return billInItemService.update(billInItemDTO);
    //}
    //
    //@PostMapping("/status/update")
    //public Integer updateStatus(@RequestBody BillInItemStatusUpdater billInItemStatusUpdater) {
    //    return billInItemService.updateStatus(billInItemStatusUpdater);
    //}

}
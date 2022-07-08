package com.mall.controller;

import com.mall.entity.OrderMaster;
import com.mall.query.OrderMasterQuery;
import com.mall.service.OrderMasterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-30 08:49
 */
@RestController
@RequestMapping("/api/ordermaster")
public class OrderMasterController {

    @Resource
    private OrderMasterService orderMasterService;

    @PostMapping("/page")
    public List<OrderMaster> getPage(@RequestBody OrderMasterQuery orderMasterQuery) {
        return orderMasterService.getPage(orderMasterQuery);
    }
}

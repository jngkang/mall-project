package com.mall.service.impl;

import com.mall.entity.OrderMaster;
import com.mall.dao.OrderMasterDao;
import com.mall.query.OrderMasterQuery;
import com.mall.service.OrderMasterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 16:01
 */
@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Resource
    private OrderMasterDao orderMasterDao;

    @Override
    public List<OrderMaster> getPage(OrderMasterQuery orderMasterQuery) {
        return orderMasterDao.select(orderMasterQuery);
    }

}

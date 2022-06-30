package com.mall.service;

import com.mall.model.OrderMaster;
import com.mall.model.query.OrderMasterQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public interface OrderMasterService {

    public List<OrderMaster> getPage(OrderMasterQuery orderMasterQuery);

}

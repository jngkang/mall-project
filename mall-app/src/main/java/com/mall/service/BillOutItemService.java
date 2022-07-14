package com.mall.service;

import com.mall.converter.BillOutItemMapper;
import com.mall.dao.BillOutItemDao;
import com.mall.entity.BillOutItem;
import com.mall.model.BillOutItemDTO;
import com.mall.model.ProductDTO;
import com.mall.query.BillOutItemQuery;
import com.mall.query.BillOutItemQueryDTO;
import com.mall.query.ProductQuery;
import com.mall.threadlocal.CurrentThreadLocal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillOutItemService {

    @Resource
    private BillOutItemDao billOutItemDao;

    @Resource
    private ProductService productService;

    public List<BillOutItemDTO> select(BillOutItemQueryDTO billOutItemQueryDTO) {
        BillOutItemQuery billOutItemQuery = BillOutItemMapper.MAPPER.queryDTO2Query(billOutItemQueryDTO);
        billOutItemQuery.setOrderByBlock(billOutItemQueryDTO.getOrderByBlock());
        billOutItemQuery.setPageNum(billOutItemQueryDTO.getPageNum());
        billOutItemQuery.setPageSize(billOutItemQueryDTO.getPageSize());
        List<BillOutItem> billOutItems = billOutItemDao.select(billOutItemQuery);

        List<BillOutItemDTO> res = BillOutItemMapper.MAPPER.beanList2DTOList(billOutItems);
        for (BillOutItemDTO r : res) {
            List<ProductDTO> productDTOS = productService.select(ProductQuery.builder().build());
            r.setProductName(productDTOS.get(0).getName());
            r.setProductImg(productDTOS.get(0).getImg());
        }
        return res;
    }

    public Integer insert(BillOutItemDTO billOutItemDTO) {
        BillOutItem billOutItem = BillOutItemMapper.MAPPER.dto2bean(billOutItemDTO);
        billOutItem.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
        billOutItem.setLastUpdateTime(LocalDateTime.now());
        return billOutItemDao.insert(billOutItem);
    }

    public Integer update(BillOutItemDTO billOutItemDTO) {
        BillOutItem billOutItem = BillOutItemMapper.MAPPER.dto2bean(billOutItemDTO);
        billOutItem.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
        billOutItem.setLastUpdateTime(LocalDateTime.now());
        return billOutItemDao.update(billOutItem);
    }

}
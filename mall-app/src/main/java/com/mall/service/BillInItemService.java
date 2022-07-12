package com.mall.service;

import com.mall.converter.BillInItemMapper;
import com.mall.dao.BillInItemDao;
import com.mall.entity.BillInItem;
import com.mall.model.BillInItemDTO;
import com.mall.model.ProductDTO;
import com.mall.query.BillInItemQuery;
import com.mall.query.BillInItemQueryDTO;
import com.mall.query.ProductQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BillInItemService {

    @Resource
    private BillInItemDao billInItemDao;

    @Resource
    private ProductService productService;

    public List<BillInItemDTO> select(BillInItemQueryDTO billInItemQueryDTO) {
        BillInItemQuery billInItemQuery = BillInItemMapper.MAPPER.queryDTO2Query(billInItemQueryDTO);
        billInItemQuery.setOrderByBlock(billInItemQueryDTO.getOrderByBlock());
        billInItemQuery.setPageNum(billInItemQueryDTO.getPageNum());
        billInItemQuery.setPageSize(billInItemQueryDTO.getPageSize());

        List<BillInItem> billInItems = billInItemDao.select(billInItemQuery);
        List<BillInItemDTO> billInItemDTOS = BillInItemMapper.MAPPER.beanList2DTOList(billInItems);
        for (BillInItemDTO billInItemDTO : billInItemDTOS) {
            List<ProductDTO> productDTOS = productService.select(ProductQuery.builder().id(Long.parseLong(billInItemDTO.getProductId().toString())).build());
            billInItemDTO.setProductImg(productDTOS.get(0).getImg());
            billInItemDTO.setProductName(productDTOS.get(0).getName());
        }
        return billInItemDTOS;
    }

    //public Integer insert(BillInItemDTO billInItemDTO) {
    //    if (ObjectUtil.isEmpty(billInItemDTO.getStatus())) {
    //        billInItemDTO.setStatus(1);
    //    }
    //    BillInItem billInItem = new BillInItem();
    //    billInItem.setStatus(billInItemDTO.getStatus());
    //    billInItem.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
    //    billInItem.setLastUpdateTime(LocalDateTime.now());
    //
    //    // TODO 将BillInItemDTO的数据转存到BillInItem中
    //
    //    return billInItemDao.insert(billInItem);
    //}
    //
    //public Integer update(BillInItemDTO billInItemDTO) {
    //    if (ObjectUtil.isEmpty(billInItemDTO.getStatus())) {
    //        billInItemDTO.setStatus(1);
    //    }
    //    BillInItem billInItem = new BillInItem();
    //    billInItem.setStatus(billInItemDTO.getStatus());
    //    billInItem.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
    //    billInItem.setLastUpdateTime(LocalDateTime.now());
    //
    //    // TODO 将BillInItemDTO的数据转存到BillInItem中
    //
    //    return billInItemDao.update(billInItem);
    //}

}
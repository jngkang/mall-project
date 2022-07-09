package com.mall.service;

import com.mall.entity.BillItem;
import com.mall.entity.BillMaster;
import com.mall.mapper.BillItemMapper;
import com.mall.mapper.BillMasterMapper;
import com.mall.model.BillItemDTO;
import com.mall.model.BillMasterDTO;
import com.mall.query.BillMasterQuery;
import com.mall.query.BillMasterQueryDTO;
import com.mall.threadlocal.CurrentThreadLocal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillMasterService {

    @Resource
    private BillMasterMapper billMasterMapper;

    @Resource
    private BillItemMapper billItemMapper;

    public List<BillMasterDTO> select(BillMasterQueryDTO billMasterQueryDTO) {
        List<BillMasterDTO> res = new ArrayList<>();

        BillMasterQuery billMasterQuery = new BillMasterQuery();
        billMasterQuery.setOrderByBlock(billMasterQueryDTO.getOrderByBlock());
        billMasterQuery.setPageNum(billMasterQueryDTO.getPageNum());
        billMasterQuery.setPageSize(billMasterQueryDTO.getPageSize());
        billMasterQuery.setId(billMasterQueryDTO.getId());
        billMasterQuery.setBillNo(billMasterQueryDTO.getBillNo());
        billMasterQuery.setVendorId(billMasterQueryDTO.getVendorId());
        billMasterQuery.setBillDate(billMasterQueryDTO.getBillDate());
        List<BillMaster> billMasters = billMasterMapper.select(billMasterQuery);

        for (BillMaster billMaster : billMasters) {
            BillMasterDTO billMasterDTO = new BillMasterDTO();
            billMasterDTO.setId(billMaster.getId());
            billMasterDTO.setBillNo(billMaster.getBillNo());
            billMasterDTO.setVendorId(billMaster.getVendorId());
            billMasterDTO.setBillDate(billMaster.getBillDate());
            // TODO 需要进行子项查询，并将结果进行赋值
            billMasterDTO.setBillItemList(null);
            res.add(billMasterDTO);
        }
        return res;
    }

    @Transactional
    public Integer insert(BillMasterDTO billMasterDTO) {
        if (billMasterDTO.getBillNo() == null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            billMasterDTO.setBillNo("R" + LocalDateTime.now().format(dtf));
        }
        BillMaster billMaster = new BillMaster();
        billMaster.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
        billMaster.setLastUpdateTime(LocalDateTime.now());
        billMaster.setBillNo(billMasterDTO.getBillNo());
        billMaster.setVendorId(billMasterDTO.getVendorId());
        billMaster.setBillDate(billMasterDTO.getBillDate());
        Integer res = billMasterMapper.insert(billMaster);
        for (BillItemDTO billItemDTO : billMasterDTO.getBillItemList()) {
            BillItem billItem = new BillItem();
            billItem.setProductId(billItemDTO.getId());
            billItem.setQty(billItemDTO.getQty());
            billItem.setBillMasterId(billMaster.getId());
            billItem.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
            billItem.setLastUpdateTime(LocalDateTime.now());
            billItemMapper.insert(billItem);
        }
        return res;
    }

    //public Integer update(BillMasterDTO billMasterDTO) {
    //    if (ObjectUtil.isEmpty(billMasterDTO.getStatus())) {
    //        billMasterDTO.setStatus(1);
    //    }
    //    BillMaster billMaster = new BillMaster();
    //    billMaster.setStatus(billMasterDTO.getStatus());
    //    billMaster.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
    //    billMaster.setLastUpdateTime(LocalDateTime.now());
    //
    //    // TODO 将BillMasterDTO的数据转存到BillMaster中
    //
    //    return billMasterMapper.update(billMaster);
    //}

}
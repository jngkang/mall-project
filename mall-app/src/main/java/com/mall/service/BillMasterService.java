package com.mall.service;

import cn.hutool.core.util.ObjectUtil;
import com.mall.dao.BillItemDao;
import com.mall.dao.BillMasterDao;
import com.mall.dao.InventoryDao;
import com.mall.dao.InventoryHistoryDao;
import com.mall.entity.BillItem;
import com.mall.entity.BillMaster;
import com.mall.entity.Inventory;
import com.mall.entity.InventoryHistory;
import com.mall.enums.BillType;
import com.mall.globel.Const;
import com.mall.model.BillItemDTO;
import com.mall.model.BillMasterDTO;
import com.mall.query.BillMasterQuery;
import com.mall.query.BillMasterQueryDTO;
import com.mall.query.InventoryQuery;
import com.mall.threadlocal.CurrentThreadLocal;
import org.springframework.data.redis.core.ValueOperations;
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
    private BillMasterDao billMasterDao;

    @Resource
    private BillItemDao billItemDao;

    @Resource
    private InventoryDao inventoryDao;

    @Resource
    private InventoryHistoryDao inventoryHistoryDao;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Integer> valueOperations;

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
        List<BillMaster> billMasters = billMasterDao.select(billMasterQuery);

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
    public Integer insert(BillMasterDTO billMasterDTO) throws Exception {
        return insertBill(billMasterDTO);
    }

    private Integer insertBill(BillMasterDTO billMasterDTO) throws Exception {
        int res = 0;
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
        res = billMasterDao.insert(billMaster);

        for (BillItemDTO billItemDTO : billMasterDTO.getBillItemList()) {
            BillItem billItem = new BillItem();
            billItem.setProductId(billItemDTO.getId());
            billItem.setQty(billItemDTO.getQty());
            billItem.setBillMasterId(billMaster.getId());
            billItem.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
            billItem.setLastUpdateTime(LocalDateTime.now());
            res = billItemDao.insert(billItem);

            List<Inventory> inventories = inventoryDao.select(InventoryQuery.builder().productId(billItemDTO.getId()).build());
            Inventory inventoryOld = null;

            Inventory inventory = new Inventory();
            inventory.setProductId(billItemDTO.getId());
            inventory.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
            inventory.setLastUpdateTime(LocalDateTime.now());
            if (ObjectUtil.isEmpty(inventories)) {
                inventory.setAccountQty(billItemDTO.getQty());
                inventory.setQty(0);
                inventory.setSoldQty(0);
                inventory.setVersion(1);
                res = inventoryDao.insert(inventory);
            } else {
                inventoryOld = inventories.get(0);
                inventory.setId(inventoryOld.getId());
                inventory.setAccountQty(inventoryOld.getAccountQty() + billItemDTO.getQty());
                inventory.setQty(inventoryOld.getQty());
                inventory.setSoldQty(inventoryOld.getSoldQty());
                inventory.setVersion(inventoryOld.getVersion());
                res = inventoryDao.update(inventory);
            }
            if (res == 0) {
                throw new Exception("商品ID" + inventory.getProductId() + "操作失败，请稍后尝试。");
            }

            InventoryHistory inventoryHistory = new InventoryHistory();
            inventoryHistory.setProductId(billItemDTO.getId());
            inventoryHistory.setOptionQty(billItemDTO.getQty());
            inventoryHistory.setOptionType(BillType.BILL_IN.getCode());
            inventoryHistory.setRefNo(billMaster.getBillNo());
            inventoryHistory.setCreateBy(CurrentThreadLocal.get().getUsername());
            inventoryHistory.setCreateTime(LocalDateTime.now());
            if (ObjectUtil.isEmpty(inventoryOld)) {
                inventoryHistory.setAccountQty0(0);
                inventoryHistory.setQty0(0);
                inventoryHistory.setSoldQty0(0);
            } else {
                inventoryHistory.setAccountQty0(inventoryOld.getAccountQty());
                inventoryHistory.setQty0(inventoryOld.getQty());
                inventoryHistory.setSoldQty0(inventoryOld.getSoldQty());
            }
            inventoryHistory.setAccountQty1(inventory.getAccountQty());
            inventoryHistory.setQty1(inventory.getQty());
            inventoryHistory.setSoldQty1(inventory.getSoldQty());
            res = inventoryHistoryDao.insert(inventoryHistory);

            valueOperations.increment(Const.PRODUCT_PROXY + billItemDTO.getId(), billItemDTO.getQty());
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
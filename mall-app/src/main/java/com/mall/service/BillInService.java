package com.mall.service;

import cn.hutool.core.util.ObjectUtil;
import com.mall.dao.BillInDao;
import com.mall.dao.BillInItemDao;
import com.mall.dao.InventoryDao;
import com.mall.dao.InventoryHistoryDao;
import com.mall.entity.BillIn;
import com.mall.entity.BillInItem;
import com.mall.entity.Inventory;
import com.mall.entity.InventoryHistory;
import com.mall.enums.BillType;
import com.mall.globel.Const;
import com.mall.model.BillInDTO;
import com.mall.model.BillInItemDTO;
import com.mall.model.VendorDTO;
import com.mall.query.BillInQuery;
import com.mall.query.BillInQueryDTO;
import com.mall.query.InventoryQuery;
import com.mall.query.VendorQueryDTO;
import com.mall.threadlocal.CurrentThreadLocal;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillInService {

    @Resource
    private BillInDao billInDao;

    @Resource
    private BillInItemDao billInItemDao;

    @Resource
    private InventoryDao inventoryDao;

    @Resource
    private VendorService vendorService;

    @Resource
    private InventoryHistoryDao inventoryHistoryDao;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Integer> valueOperations;

    public List<BillInDTO> select(BillInQueryDTO billInQueryDTO) {
        List<BillInDTO> res = new ArrayList<>();

        BillInQuery billInQuery = new BillInQuery();
        billInQuery.setOrderByBlock(billInQueryDTO.getOrderByBlock());
        billInQuery.setPageNum(billInQueryDTO.getPageNum());
        billInQuery.setPageSize(billInQueryDTO.getPageSize());
        billInQuery.setId(billInQueryDTO.getId());
        billInQuery.setBillNo(billInQueryDTO.getBillNo());
        billInQuery.setVendorId(billInQueryDTO.getVendorId());
        billInQuery.setBillDate(billInQueryDTO.getBillDate());
        List<BillIn> billIns = billInDao.select(billInQuery);

        // 查询所有供应商id和name，放置到Map中
        List<VendorDTO> vendorDTOS = vendorService.select(new VendorQueryDTO());
        Map<Long, String> VendorNames = new HashMap<>();
        for (VendorDTO vendorDTO : vendorDTOS) {
            VendorNames.put(vendorDTO.getId(), vendorDTO.getName());
        }

        for (BillIn billIn : billIns) {
            BillInDTO billInDTO = new BillInDTO();
            billInDTO.setId(billIn.getId());
            billInDTO.setBillNo(billIn.getBillNo());
            billInDTO.setVendorId(billIn.getVendorId());
            billInDTO.setVendorName(VendorNames.get(Long.parseLong(billIn.getVendorId().toString())));
            billInDTO.setBillDate(billIn.getBillDate());
            billInDTO.setLastUpdateBy(CurrentThreadLocal.get().getNickname());
            billInDTO.setLastUpdateTime(LocalDateTime.now());
            res.add(billInDTO);
        }
        return res;
    }

    @Transactional
    public Integer insert(BillInDTO billInDTO) throws Exception {
        int res = 0;
        if (billInDTO.getBillNo() == null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            billInDTO.setBillNo("R" + LocalDateTime.now().format(dtf));
        }
        BillIn billIn = new BillIn();
        billIn.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
        billIn.setLastUpdateTime(LocalDateTime.now());
        billIn.setBillNo(billInDTO.getBillNo());
        billIn.setVendorId(billInDTO.getVendorId());
        billIn.setBillDate(billInDTO.getBillDate());
        res = billInDao.insert(billIn);

        for (BillInItemDTO billInItemDTO : billInDTO.getBillItemList()) {
            BillInItem billInItem = new BillInItem();
            billInItem.setProductId(billInItemDTO.getId());
            billInItem.setQty(billInItemDTO.getQty());
            billInItem.setBillId(billIn.getId());
            billInItem.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
            billInItem.setLastUpdateTime(LocalDateTime.now());
            res = billInItemDao.insert(billInItem);

            List<Inventory> inventories = inventoryDao.select(InventoryQuery.builder().productId(billInItemDTO.getId()).build());
            Inventory inventoryOld = null;

            Inventory inventory = new Inventory();
            inventory.setProductId(billInItemDTO.getId());
            inventory.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
            inventory.setLastUpdateTime(LocalDateTime.now());
            if (ObjectUtil.isEmpty(inventories)) {
                inventory.setAccountQty(billInItemDTO.getQty());
                inventory.setQty(0);
                inventory.setSoldQty(0);
                inventory.setVersion(1);
                res = inventoryDao.insert(inventory);
            } else {
                inventoryOld = inventories.get(0);
                inventory.setId(inventoryOld.getId());
                inventory.setAccountQty(inventoryOld.getAccountQty() + billInItemDTO.getQty());
                inventory.setQty(inventoryOld.getQty());
                inventory.setSoldQty(inventoryOld.getSoldQty());
                inventory.setVersion(inventoryOld.getVersion());
                res = inventoryDao.update(inventory);
            }
            if (res == 0) {
                throw new Exception("商品ID" + inventory.getProductId() + "操作失败，请稍后尝试。");
            }

            InventoryHistory inventoryHistory = new InventoryHistory();
            inventoryHistory.setProductId(billInItemDTO.getId());
            inventoryHistory.setOptionQty(billInItemDTO.getQty());
            inventoryHistory.setOptionType(BillType.BILL_IN.getCode());
            inventoryHistory.setRefNo(billIn.getBillNo());
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

            valueOperations.increment(Const.PRODUCT_PROXY + billInItemDTO.getId(), billInItemDTO.getQty());
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
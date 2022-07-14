package com.mall.service;

import cn.hutool.core.util.ObjectUtil;
import com.mall.converter.BillOutMapper;
import com.mall.dao.BillOutDao;
import com.mall.dao.BillOutItemDao;
import com.mall.dao.InventoryDao;
import com.mall.dao.InventoryHistoryDao;
import com.mall.entity.BillOut;
import com.mall.entity.BillOutItem;
import com.mall.entity.Inventory;
import com.mall.entity.InventoryHistory;
import com.mall.enums.BillType;
import com.mall.globel.Const;
import com.mall.model.BillOutDTO;
import com.mall.model.BillOutItemDTO;
import com.mall.model.VendorDTO;
import com.mall.query.BillOutQuery;
import com.mall.query.BillOutQueryDTO;
import com.mall.query.InventoryQuery;
import com.mall.query.VendorQueryDTO;
import com.mall.threadlocal.CurrentThreadLocal;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillOutService {

    @Resource
    private BillOutDao billOutDao;

    @Resource
    private BillOutItemDao billOutItemDao;

    @Resource
    private InventoryDao inventoryDao;

    @Resource
    private InventoryHistoryDao inventoryHistoryDao;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Integer> valueOperations;

    @Resource
    private VendorService vendorService;

    public List<BillOutDTO> select(BillOutQueryDTO billOutQueryDTO) {
        BillOutQuery billOutQuery = BillOutMapper.MAPPER.queryDTO2Query(billOutQueryDTO);
        billOutQuery.setOrderByBlock(billOutQueryDTO.getOrderByBlock());
        billOutQuery.setPageNum(billOutQueryDTO.getPageNum());
        billOutQuery.setPageSize(billOutQueryDTO.getPageSize());
        List<BillOut> billOuts = billOutDao.select(billOutQuery);

        List<VendorDTO> vendorDTOS = vendorService.select(new VendorQueryDTO());
        Map<Long, String> vendors = new HashMap<>();
        for (VendorDTO vendorDTO : vendorDTOS) {
            vendors.put(vendorDTO.getId(), vendorDTO.getName());
        }

        List<BillOutDTO> res = BillOutMapper.MAPPER.beanList2DTOList(billOuts);
        for (BillOutDTO r : res) {
            r.setVendorName(vendors.get(Long.parseLong(r.getVendorId().toString())));
        }
        return res;
    }

    @Transactional
    public Integer insert(BillOutDTO billOutDTO) throws Exception {
        int res = 0;
        if (billOutDTO.getBillNo() == null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            billOutDTO.setBillNo("C" + LocalDateTime.now().format(dtf));
        }
        BillOut billOut = new BillOut();
        billOut.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
        billOut.setLastUpdateTime(LocalDateTime.now());
        billOut.setBillNo(billOutDTO.getBillNo());
        billOut.setVendorId(Math.toIntExact(CurrentThreadLocal.get().getId()));
        billOut.setBillDate(billOutDTO.getBillDate());
        res = billOutDao.insert(billOut);

        for (BillOutItemDTO billOutItemDTO : billOutDTO.getBillItemList()) {
            Integer qtyOld = valueOperations.get(Const.PRODUCT_PROXY + billOutItemDTO.getId());
            if (qtyOld < billOutItemDTO.getQty()) {
                throw new RuntimeException("库存量不足");
            }

            BillOutItem billOutItem = new BillOutItem();
            billOutItem.setProductId(billOutItemDTO.getId());
            billOutItem.setQty(billOutItemDTO.getQty());
            billOutItem.setBillId(billOut.getId());
            billOutItem.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
            billOutItem.setLastUpdateTime(LocalDateTime.now());
            res = billOutItemDao.insert(billOutItem);

            List<Inventory> inventories = inventoryDao.select(InventoryQuery.builder().productId(billOutItemDTO.getId()).build());
            Inventory inventoryOld = inventories.get(0);

            Inventory inventory = new Inventory();
            inventory.setProductId(billOutItemDTO.getId());
            inventory.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
            inventory.setLastUpdateTime(LocalDateTime.now());

            inventory.setId(inventoryOld.getId());
            inventory.setAccountQty(inventoryOld.getAccountQty() - billOutItemDTO.getQty());
            inventory.setQty(inventoryOld.getQty());
            inventory.setSoldQty(inventoryOld.getSoldQty());
            inventory.setVersion(inventoryOld.getVersion());
            res = inventoryDao.update(inventory);

            if (res == 0) {
                throw new Exception("商品ID" + inventory.getProductId() + "操作失败，请稍后尝试。");
            }

            InventoryHistory inventoryHistory = new InventoryHistory();
            inventoryHistory.setProductId(billOutItemDTO.getId());
            inventoryHistory.setOptionQty(billOutItemDTO.getQty());
            inventoryHistory.setOptionType(BillType.BILL_OUT.getCode());
            inventoryHistory.setRefNo(billOut.getBillNo());
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

            valueOperations.decrement(Const.PRODUCT_PROXY + billOutItemDTO.getId(), billOutItemDTO.getQty());
        }

        return res;
    }

    public Integer update(BillOutDTO billOutDTO) {
        BillOut billOut = BillOutMapper.MAPPER.dto2bean(billOutDTO);
        billOut.setLastUpdateBy(CurrentThreadLocal.get().getUsername());
        billOut.setLastUpdateTime(LocalDateTime.now());
        return billOutDao.update(billOut);
    }

}
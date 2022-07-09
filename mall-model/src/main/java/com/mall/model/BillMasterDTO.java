package com.mall.model;

import com.mall.annotation.Column;
import com.mall.annotation.PK;
import com.mall.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 入库单管理(TbBillMaster)实体类
 *
 * @author makejava
 * @since 2022-07-09 08:26:31
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "tb_bill_master")
public class BillMasterDTO implements Serializable {

    /**
     * ID
     */
    @PK
    private Integer id;
    /**
     * 单据号
     */
    @Column(value = "bill_no")
    private String billNo;
    /**
     * 供应商ID
     */
    @Column(value = "vendor_id")
    private Integer vendorId;
    /**
     * 单据日期
     */
    @Column(value = "bill_date")
    private LocalDateTime billDate;

    private List<BillItemDTO> billItemList;

}


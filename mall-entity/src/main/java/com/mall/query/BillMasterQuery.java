package com.mall.query;

import com.mall.annotation.Column;
import com.mall.annotation.EQ;
import com.mall.annotation.PK;
import com.mall.annotation.Table;
import com.mall.model.AbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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
public class BillMasterQuery extends AbstractQuery {

    /**
     * ID
     */
    @PK
    @EQ
    private Integer id;
    /**
     * 单据号
     */
    @Column(value = "bill_no")
    @EQ
    private String billNo;
    /**
     * 供应商ID
     */
    @Column(value = "vendor_id")
    @EQ
    private Integer vendorId;
    /**
     * 单据日期
     */
    @Column(value = "bill_date")
    @EQ
    private Date billDate;

}


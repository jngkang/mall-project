package com.mall.query;

import com.mall.annotation.Column;
import com.mall.annotation.EQ;
import com.mall.annotation.Like;
import com.mall.annotation.PK;
import com.mall.annotation.Table;
import com.mall.model.AbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 出库单管理(BillOut)实体类
 *
 * @author makejava
 * @since 2022-07-13 19:37:09
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "bill_out")
public class BillOutQueryDTO extends AbstractQuery {

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
    @Like
    private String billNo;
    /**
     * 供应商ID
     */
    @Column(value = "vendor_id")
    @EQ
    private Integer vendorId;
    private Long categoryId;
    /**
     * 单据日期
     */
    @Column(value = "bill_date")
    @EQ
    private LocalDateTime billDate;
    /**
     * 最后更新者
     */
    @Column(value = "last_update_by")
    private String lastUpdateBy;
    /**
     * 最后更新时间
     */
    @Column(value = "last_update_time")
    private LocalDateTime lastUpdateTime;

}


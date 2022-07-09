package com.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

/**
 * 入库商品表(TbBillItem)实体类
 *
 * @author makejava
 * @since 2022-07-09 08:27:10
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "tb_bill_item")
public class BillItem implements Serializable {

    /**
     * ID
     */
    @PK
    private Integer id;
    /**
     * 商品ID
     */
    @Column(value = "product_id")
    private Integer productId;
    /**
     * 商品数量
     */
    private Integer qty;
    /**
     * 入库单据ID
     */
    @Column(value = "bill_master_id")
    private Integer billMasterId;
    /**
     * 最后更新着
     */
    @Column(value = "last_update_by")
    private String lastUpdateBy;
    /**
     * 最后更新时间
     */
    @Column(value = "last_update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;

}


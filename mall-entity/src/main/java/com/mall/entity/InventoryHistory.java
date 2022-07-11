package com.mall.entity;

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
 * (Inventoryhisory)实体类
 *
 * @author makejava
 * @since 2022-07-11 15:48:56
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "inventory_history")
public class InventoryHistory implements Serializable {

    /**
     * ID
     */
    @PK
    private Integer id;
    /**
     * 商品ID
     */
    private Integer productId;
    /**
     * 操作数量
     */
    private Integer optionQty;
    /**
     * 操作类型
     */
    private Integer optionType;
    /**
     * 操作之前财务数量
     */
    private Integer accountQty0;
    /**
     * 操作之后财务数量
     */
    private Integer accountQty1;
    /**
     * 操作之前可售数量
     */
    private Integer qty0;
    /**
     * 操作之后可售数量
     */
    private Integer qty1;
    /**
     * 操作之前已售数量
     */
    private Integer soldQty0;
    /**
     * 操作之后已售数量
     */
    private Integer soldQty1;
    /**
     * 单据号
     */
    private String refNo;

    private String createBy;

    private LocalDateTime createTime;

}


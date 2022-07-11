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
 * (Inventory)实体类
 *
 * @author makejava
 * @since 2022-07-11 15:48:48
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "inventory")
public class Inventory implements Serializable {

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
     * 财务数量、进货数量
     */
    private Integer accountQty;
    /**
     * 可售数量
     */
    private Integer qty;
    /**
     * 已售数量
     */
    private Integer soldQty;

    private String lastUpdateBy;

    private LocalDateTime lastUpdateTime;

    private Integer version;

}


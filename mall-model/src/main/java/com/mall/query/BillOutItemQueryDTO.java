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

import java.time.LocalDateTime;

/**
 * 出库商品表(BillOutItem)实体类
 *
 * @author makejava
 * @since 2022-07-13 19:37:17
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "bill_out_item")
public class BillOutItemQueryDTO extends AbstractQuery {

    /**
     * ID
     */
    @PK
    @EQ
    private Integer id;
    /**
     * 商品ID
     */
    @Column(value = "product_id")
    @EQ
    private Integer productId;
    /**
     * 商品数量
     */
    @EQ
    private Integer qty;
    /**
     * 入库单据ID
     */
    @Column(value = "bill_id")
    @EQ
    private Integer billId;
    /**
     * 最后更新着
     */
    @Column(value = "last_update_by")
    private String lastUpdateBy;
    /**
     * 最后更新时间
     */
    @Column(value = "last_update_time")
    private LocalDateTime lastUpdateTime;

}


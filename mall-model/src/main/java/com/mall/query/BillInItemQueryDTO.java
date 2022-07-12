package com.mall.query;

import com.mall.annotation.Column;
import com.mall.annotation.EQ;
import com.mall.annotation.PK;
import com.mall.annotation.Table;
import com.mall.model.AbstractQueryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@Table(value = "bill_in_item")
public class BillInItemQueryDTO extends AbstractQueryDTO {

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

}


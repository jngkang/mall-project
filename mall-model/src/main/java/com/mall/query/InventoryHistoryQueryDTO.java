package com.mall.query;

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
public class InventoryHistoryQueryDTO extends AbstractQueryDTO {

    /**
     * ID
     */
    @PK
    @EQ
    private Integer id;
    /**
     * 商品ID
     */
    @EQ
    private Integer productId;
    /**
     * 操作数量
     */
    @EQ
    private Integer optionQty;
    /**
     * 操作类型
     */
    @EQ
    private Integer optionType;
    /**
     * 单据号
     */
    @EQ
    private String refNo;

}


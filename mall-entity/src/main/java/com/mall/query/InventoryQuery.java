package com.mall.query;

import com.mall.annotation.EQ;
import com.mall.annotation.Table;
import com.mall.model.AbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class InventoryQuery extends AbstractQuery {

    /**
     * ID
     */
    @EQ
    private Integer id;
    /**
     * 商品ID
     */
    @EQ
    private Integer productId;

}


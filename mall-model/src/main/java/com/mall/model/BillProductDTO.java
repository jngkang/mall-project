package com.mall.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 订单中商品信息
 *
 * @author JngKang
 * @date 2022-07-14 14:00
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BillProductDTO {
    /**
     * 商品ID
     */
    private Integer id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品图片
     */
    private String img;
    /**
     * 商品数量
     */
    private Integer qty;
}

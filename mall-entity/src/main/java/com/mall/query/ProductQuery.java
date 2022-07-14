package com.mall.query;

import com.mall.annotation.Column;
import com.mall.annotation.EQ;
import com.mall.annotation.IN;
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

/**
 * 商品表(Product)实体类
 *
 * @author makejava
 * @since 2022-07-05 15:59:59
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "tb_product")
public class ProductQuery extends AbstractQuery {

    /**
     * ID
     */
    @PK
    @EQ
    private Long id;

    @Column(value = "id")
    @IN
    private Long[] ids;
    /**
     * 列别编号
     */
    @Column(value = "category_id")
    @EQ
    private Long categoryId;
    /**
     * 商品名称
     */
    @Like
    private String name;
    /**
     * 商品价格
     */
    private Double price;
    /**
     * 商品内容
     */
    @Like
    private String brief;
    /**
     * 状态：1上架，2下架
     */
    @EQ
    private Integer status = 1;
}


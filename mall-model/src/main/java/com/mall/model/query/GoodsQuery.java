package com.mall.model.query;

import com.mall.annotation.Column;
import com.mall.annotation.EQ;
import com.mall.annotation.Like;
import com.mall.annotation.PK;
import com.mall.annotation.Table;
import com.mall.model.bean.AbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品表(TbGoods)实体类
 *
 * @author makejava
 * @since 2022-07-02 10:19:07
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "tb_goods")
public class GoodsQuery extends AbstractQuery {

    /**
     * ID
     */
    @PK
    @EQ
    private Integer id;
    /**
     * 商品名称
     */
    @Like
    private String name;
    /**
     * 副标题
     */
    @Like
    private String caption;
    /**
     * 列别编号
     */
    @Column("category_id")
    @EQ
    private Integer categoryId;
    /**
     * 单位
     */
    @EQ
    private String unit;
    /**
     * 产地
     */
    @Like
    private String addr;
    /**
     * 商品内容
     */
    @Like
    private String info;

}


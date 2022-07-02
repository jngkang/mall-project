package com.mall.model;

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
@Table(value="tb_goods")
public class Goods implements Serializable {

    /**
     * ID
     */
    @PK
    private Integer id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 副标题
     */
    private String caption;
    /**
     * 列别编号
     */
    @Column("category_id")
    private Integer categoryId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 产地
     */
    private String addr;
    /**
     * 商品内容
     */
    private String info;

}


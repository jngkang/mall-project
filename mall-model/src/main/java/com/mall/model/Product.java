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
import java.time.LocalDateTime;

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
public class Product implements Serializable {

    /**
     * ID
     */
    @PK
    private Long id;
    /**
     * 列别编号
     */
    @Column(value = "category_id")
    private Long categoryId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品图片
     */
    private String img;
    /**
     * 商品价格
     */
    private Double price;
    /**
     * 商品内容
     */
    private String brief;
    /**
     * 排序
     */
    private Integer seq;
    /**
     * 商品状态
     */
    private Integer status;
    /**
     * 最后更新者
     */
    @Column(value = "last_update_by")
    private String lastUpdateBy;
    /**
     * 最后更新时间
     */
    @Column(value = "last_update_time")
    private LocalDateTime lastUpdateTime;

}


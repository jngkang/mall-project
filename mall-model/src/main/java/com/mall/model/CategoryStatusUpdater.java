package com.mall.model;

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
 * 商品类别表(TbCategory)实体类
 *
 * @author makejava
 * @since 2022-06-30 15:16:21
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "tb_category")
public class CategoryStatusUpdater implements Serializable {

    /**
     * 类别编号
     */
    @PK
    private Long id;
    /**
     * 状态：1上架，2下架
     */
    private Integer status;

}


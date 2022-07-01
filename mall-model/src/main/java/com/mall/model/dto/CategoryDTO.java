package com.mall.model.dto;

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
public class CategoryDTO implements Serializable {

    /**
     * 类别名称
     */
    private String name;
    /**
     * 商品图片
     */
    private String img;
    private String imgName;
    /**
     * 显示优化级
     */
    private Long priority;
    /**
     * 父类别编号
     */
    private Long pid;
    /**
     * 状态：1上架，2下架
     */
    private Integer status;
}


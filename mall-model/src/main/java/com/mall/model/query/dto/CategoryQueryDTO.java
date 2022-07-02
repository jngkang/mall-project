package com.mall.model.query.dto;

import com.mall.model.bean.AbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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
public class CategoryQueryDTO extends AbstractQuery {

    /**
     * 类别编号
     */
    private Long id;
    /**
     * 类别名称
     */
    private String name;
    /**
     * 父类别编号
     */
    private Long pid;
    /**
     * 状态：1上架，2下架
     */
    private Integer status = 1;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 检索的深度
     */
    private Integer deep = Integer.MAX_VALUE;
    ;
}


package com.mall.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.model.bean.AbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

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
public class CategoryQuery extends AbstractQuery {

    /**
     * 类别编号
     */
    private Long id;
    /**
     * 类别名称
     */
    private String name;
    /**
     * 商品图片
     */
    private String img;
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
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate updateTime;

}


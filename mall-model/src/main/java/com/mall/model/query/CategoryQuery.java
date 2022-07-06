package com.mall.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.annotation.EQ;
import com.mall.annotation.Like;
import com.mall.model.AbstractQuery;
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
public class CategoryQuery extends AbstractQuery {

    /**
     * 类别编号
     */
    @EQ
    private Long id;
    /**
     * 类别名称
     */
    @Like
    private String name;
    /**
     * 父类别编号
     */
    @EQ
    private Long pid;
    /**
     * 状态：1上架，2下架
     */
    @EQ
    private Integer status = 1;
    /**
     * 更新人
     */
    @EQ
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}


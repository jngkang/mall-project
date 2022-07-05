package com.mall.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.annotation.Column;
import com.mall.annotation.PK;
import com.mall.annotation.Table;
import com.mall.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
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
@Table(value = "tb_category")
public class CategoryDTO implements Serializable {

    @PK
    private Long id;
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

    private String statusX;

    /**
     * 更新人
     */
    @Column("update_by")
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column("update_time")
    private LocalDateTime updateTime;

    public void setStatus(Integer status) {
        this.status = status;
        this.statusX = CategoryStatus.findByCode(status).getName();
    }
}


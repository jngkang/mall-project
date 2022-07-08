package com.mall.query;

import com.mall.annotation.EQ;
import com.mall.annotation.Like;
import com.mall.annotation.PK;
import com.mall.annotation.Table;
import com.mall.model.AbstractQueryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * (TbVendor)实体类
 *
 * @author makejava
 * @since 2022-07-07 10:28:10
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value="tb_vendor")
public class VendorQueryDTO extends AbstractQueryDTO {

    /**
     * ID
     */
    @PK
    @EQ
    private Long id;
    /**
     * 供应商名称
     */
    @Like
    private String name;
    /**
     * 状态
     */
    @EQ
    private Integer status;

}


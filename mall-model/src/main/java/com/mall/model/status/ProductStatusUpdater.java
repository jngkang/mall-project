package com.mall.model.status;

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
public class ProductStatusUpdater implements Serializable {

    /**
     * ID
     */
    @PK
    private Long id;
    /**
     * 商品状态
     */
    private Integer status;

}


package com.mall.status;

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
public class VendorStatusUpdater implements Serializable {

    /**
     * ID
     */
    @PK
    private Long id;
    /**
     * 状态
     */
    private Integer status;

}


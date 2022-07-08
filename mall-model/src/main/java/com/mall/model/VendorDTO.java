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
public class VendorDTO implements Serializable {

    /**
     * ID
     */
    @PK
    private Long id;
    /**
     * 供应商名称
     */
    private String name;
    /**
     * 省邮编
     */
    private String province;
    /**
     * 市邮编
     */
    private String city;
    /**
     * 县/区邮编
     */
    private String district;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 排序
     */
    private Integer seq;
    /**
     * 状态
     */
    private Integer status;
    private String statusX;
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


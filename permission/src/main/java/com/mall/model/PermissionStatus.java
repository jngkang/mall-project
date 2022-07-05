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
 * 权限表(Permission)实体类
 *
 * @author makejava
 * @since 2022-07-04 19:47:19
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value = "tb_permission")
public class PermissionStatus implements Serializable {

    /**
     * 编号
     */
    @PK
    private Long id;
    /**
     * 启用状态：0禁用  1启用
     */
    private Integer status;

}


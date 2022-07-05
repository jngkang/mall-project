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
public class Permission implements Serializable {

    /**
     * 编号
     */
    @PK
    private Long id;
    /**
     * 父权限ID，根节点的父权限为空
     */
    private Long pid;
    /**
     * 权限标题
     */
    private String name;
    /**
     * 权限类型
     */
    private String type;
    /**
     * 权限所能访问的资源的路径
     */
    private String url;
    /**
     * 权限所对应的图标
     */
    private String icon;
    /**
     * 排序值（默认是50）
     */
    private Integer sort;
    /**
     * 是否展开：0关闭  1展开
     */
    private Integer open;
    /**
     * 启用状态：0禁用  1启用
     */
    private Integer status;
    /**
     * 备注信息
     */
    private String info;
    /**
     * 创建时间
     */
    @Column(value = "create_time")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Column(value = "update_time")
    private LocalDateTime updateTime;


}


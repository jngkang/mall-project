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
import java.util.Date;

/**
 * 用户表(TbUser)实体类
 *
 * @author makejava
 * @since 2022-06-28 13:17:49
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(value="tb_user")
public class User implements Serializable {
    /**
     * 编号
     */
    @PK(autoIncrement = true)
    private Long id;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 生日
     */
    private Date birth;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 头像
     */
    private String avatar;
    /**
     * QQ
     */
    private String qq;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 简介
     */
    private String info;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 入职日期
     */
    @Column(value = "in_time")
    private Date inTime;
    /**
     * 离职日期
     */
    @Column(value = "out_time")
    private Date outTime;
    /**
     * 状态：1解锁 2锁定 3离职
     */
    private Integer status;
    /**
     * 创建时间
     */
    @Column(value = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(value = "update_time")
    private Date updateTime;

}


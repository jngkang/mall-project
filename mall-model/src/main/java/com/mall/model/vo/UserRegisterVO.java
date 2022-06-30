package com.mall.model.vo;

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
@Table(value = "tb_user")
public class UserRegisterVO implements Serializable {
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
    private String password2;
    /**
     * 邮箱
     */
    private String email;
    private String emailCaptcha;

}


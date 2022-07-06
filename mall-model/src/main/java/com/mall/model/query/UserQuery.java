package com.mall.model.query;

import com.mall.model.AbstractQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class UserQuery extends AbstractQuery {

    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    private String email;

}


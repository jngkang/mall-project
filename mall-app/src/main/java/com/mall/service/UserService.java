package com.mall.service;

import com.mall.model.UserLoginDTO;
import com.mall.model.UserRegisterDTO;
import org.springframework.stereotype.Service;

/**
 * @author JngKang
 * @date 2022-06-28 15:59
 */
@Service
public interface UserService {

    /**
     * 用户登录
     *
     * @param userLoginDTO 前端传回的用户名和密码
     * @return java.lang.String
     */
    public String login(UserLoginDTO userLoginDTO) throws Exception;

    /**
     * 用户注册
     *
     * @param userRegisterDTO 从前端传回的用户注册所填写的信息
     * @return java.lang.String
     */
    public String register(UserRegisterDTO userRegisterDTO) throws Exception;

    /**
     * 获取注册验证码
     *
     * @param email 验证码所需发送的邮箱
     * @return java.lang.String
     */
    public String sendEmailCaptcha(String email) throws Exception;
}

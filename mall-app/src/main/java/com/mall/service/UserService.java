package com.mall.service;

import com.mall.model.vo.UserLoginVO;
import com.mall.model.vo.UserRegisterVO;
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
     * @param userLoginVO 前端传回的用户名和密码
     * @return java.lang.String
     */
    public String login(UserLoginVO userLoginVO) throws Exception;

    /**
     * 用户注册
     *
     * @param userRegisterVO 从前端传回的用户注册所填写的信息
     * @return java.lang.String
     */
    public String register(UserRegisterVO userRegisterVO) throws Exception;

    /**
     * 获取注册验证码
     *
     * @param email 验证码所需发送的邮箱
     * @return java.lang.String
     */
    public String sendEmailCaptcha(String email) throws Exception;
}

package com.mall.controller;

import com.mall.annotation.NoAuthorization;
import com.mall.model.vo.UserLoginVO;
import com.mall.model.vo.UserRegisterVO;
import com.mall.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author JngKang
 * @date 2022-06-28 16:22
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private UserService userService;

    @NoAuthorization
    @PostMapping("login")
    public String login(@RequestBody UserLoginVO userLoginVO) throws Exception {
        return userService.login(userLoginVO);
    }

    @NoAuthorization
    @PostMapping("/register")
    public String register(@RequestBody UserRegisterVO userRegisterVO) throws Exception {
        return userService.register(userRegisterVO);
    }

    @NoAuthorization
    @PostMapping("/sendemailcaptcha")
    public String sendEmailCaptcha(@RequestBody Map<String, String> res) throws Exception {
        return userService.sendEmailCaptcha(res.get("email"));
    }
}

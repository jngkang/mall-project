package com.mall.controller;

import com.mall.annotation.NoAuthorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JngKang
 * @date 2022-07-20 16:47
 */
@RestController
public class TestController {

    @GetMapping("/test")
    @NoAuthorization
    public String test(){
        return "测试页面";
    }
}

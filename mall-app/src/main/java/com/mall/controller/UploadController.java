package com.mall.controller;

import com.mall.annotation.NoAuthorization;
import com.mall.annotation.NoWapper;
import com.mall.utils.AliOSSUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;

/**
 * @author JngKang
 * @date 2022-07-01 08:45
 */
@RestController
@MultipartConfig
@RequestMapping("/api")
@NoWapper
public class UploadController {

    @NoAuthorization
    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        String res = null;
        try {
            res = AliOSSUtil.upload(file);
        } catch (IOException e) {
            return "{\"errno\": 1,\"message\": \"" + e.getMessage() + "\"}";
        }
        return "{\"errno\": 0,\"data\": {\"url\": \"" + res + "\"}}";
    }
}
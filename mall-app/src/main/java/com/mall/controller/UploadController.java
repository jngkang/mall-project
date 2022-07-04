package com.mall.controller;

import cn.hutool.json.JSONUtil;
import com.mall.annotation.NoWapper;
import com.mall.utils.AliOSSUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JngKang
 * @date 2022-07-01 08:45
 */
@RestController
@MultipartConfig
@RequestMapping("/api")
@NoWapper
public class UploadController {

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        Map<String, Object> res = new HashMap<>();
        String url = "";
        try {
            url = AliOSSUtil.upload(file);
        } catch (IOException e) {
            res.put("errno", 1);
            res.put("message", e.getMessage());
            return JSONUtil.toJsonStr(res);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("url", url);
        data.put("alt", file.getOriginalFilename());

        res.put("errno", 0);
        res.put("data", data);
        return JSONUtil.toJsonStr(res);
    }
}
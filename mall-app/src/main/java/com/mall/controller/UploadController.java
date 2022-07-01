package com.mall.controller;

import com.mall.utils.AliOSSUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class UploadController {

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return AliOSSUtil.upload(multipartFile);
    }
}

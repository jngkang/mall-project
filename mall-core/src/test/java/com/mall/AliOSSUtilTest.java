package com.mall;

import com.mall.utils.AliOSSUtil;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

class AliOSSUtilTest {

    /**
     * @param
     * @return void
     * @description 文件上传
     */
    @Test
    void upload() throws FileNotFoundException {
        //上传文件
        String url = AliOSSUtil.upload("D:\\1.txt", "2.txts");
        System.out.println(url);
    }

    @Test
    void listFileName() {
        List<String> res = AliOSSUtil.listFileName("test/");
        res.forEach(System.out::println);
    }

    @Test
    void listFileUrl() {
        List<String> res = AliOSSUtil.listFileUrl("test/");
        res.forEach(System.out::println);
    }

    @Test
    void isFileExist() {
        boolean res = AliOSSUtil.isFileExist("test/K1650520204753.jpg");
        System.out.println(res);
    }

    @Test
    void downloadFile() {
        AliOSSUtil.downloadFile("test/K1650520204753.jpg", "d:/KK.jpg");
    }

    @Test
    void delelteFile() {
        AliOSSUtil.delelteFile("test/KK1650521987347.jpg");
    }

    @Test
    void deleteFiles() {
        //List<String> keys = List.of("KK1650522195064.jpg", "KK1650522206716.jpg");
        //AliOSSUtil.deleteFiles(keys);
    }

    @Test
    void createFolder() {
        String folder = AliOSSUtil.createFolder("test2/");
        System.out.println(folder);
    }

}
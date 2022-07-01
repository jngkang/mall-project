package com.mall.config;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class AliOSSConfig {

    /**
     * 存储区域
     * 例：oss-cn-shanghai.aliyuncs.com
     */
    private String endpoint;
    /**
     * KeyId
     */
    private String accessKeyId;
    /**
     * KeySecret
     */
    private String accessKeySecret;
    /**
     * bucket存储空间名称
     */
    private String bucketName;


    private AliOSSConfig() {
        Properties properties = new Properties();
        try {
            InputStream is = AliOSSConfig.class.getResourceAsStream("/alioss.properties");
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        endpoint = properties.getProperty("aliyun.oss.endpoint");
        accessKeyId = properties.getProperty("aliyun.oss.accessKeyId");
        accessKeySecret = properties.getProperty("aliyun.oss.accessKeySecret");
        bucketName = properties.getProperty("aliyun.oss.bucketName");
    }

    private static AliOSSConfig instance = new AliOSSConfig();

    public static AliOSSConfig getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        AliOSSConfig oss = new AliOSSConfig();
        System.out.println(oss.endpoint);
        System.out.println(oss.accessKeyId);
        System.out.println(oss.accessKeySecret);
        System.out.println(oss.bucketName);
    }
}
package com.mall.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.mall.config.AliOSSConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AliOSSUtil {
    private static final String endpoint = AliOSSConfig.getInstance().getEndpoint();
    private static final String accessKeyId = AliOSSConfig.getInstance().getAccessKeyId();
    private static final String accessKeySecret = AliOSSConfig.getInstance().getAccessKeySecret();
    private static final String bucketName = AliOSSConfig.getInstance().getBucketName();

    /**
     * @param sourceFilePathName 本地文件路径
     * @param aimFilePathName    在阿里OSS中保存的路径（可以包含路径的文件名）
     * @return java.lang.String 返回上传后文件的访问路径。注：文件名称将会被重新命名，命名规则是：新文件名+当前时间戳
     * @description 将文件上传到阿里OSS
     */
    public static String upload(String sourceFilePathName, String aimFilePathName) throws FileNotFoundException {
        FileInputStream is = new FileInputStream(sourceFilePathName);

        if (aimFilePathName.startsWith("/")) {
            aimFilePathName = aimFilePathName.substring(1);
        }

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        ObjectMetadata metadata = new ObjectMetadata();
        int indexOfLastDot = aimFilePathName.lastIndexOf(".");
        String suffix = aimFilePathName.substring(indexOfLastDot);
        metadata.setContentType(getContentType(suffix));

        //避免文件覆盖
        aimFilePathName = aimFilePathName.substring(0, indexOfLastDot) + System.currentTimeMillis() + suffix;

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, aimFilePathName, is);
        //避免访问时将图片下载下来
        putObjectRequest.setMetadata(metadata);

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.putObject(putObjectRequest);

        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
        URL url = ossClient.generatePresignedUrl(bucketName, aimFilePathName, expiration);

        // 关闭ossClient
        ossClient.shutdown();

        return url.toString();
    }

    /**
     * @param multipartFile 前端传回的文件
     * @return java.lang.String 返回上传后的文件访问路径。注：文件名称将会被重新命名，命名规则是：新文件名+当前时间戳
     * @description 文件上传。需要在SpringBoot项目中使用。
     * 应用场景：网络上传头像到O
     */
    public static String upload(MultipartFile multipartFile) throws IOException {
        // 获取上传的文件的输入流
        InputStream inputStream = multipartFile.getInputStream();
        // 获取文件名称
        String fileName = multipartFile.getOriginalFilename();

        // 避免文件覆盖
        int i = fileName.lastIndexOf(".");
        String suffix = fileName.substring(i);
        fileName = fileName.substring(0, i) + System.currentTimeMillis() + suffix;

        // 把文件按照日期进行分类
        // 获取当前日期
        String datePath = DateTimeFormatter.ISO_DATE.format(LocalDate.now());
        // 拼接fileName
        fileName = datePath + "/" + fileName;

        // 如果需要上传时设置存储类型与访问权限
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));

        // 上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
        putObjectRequest.setMetadata(metadata);

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.putObject(putObjectRequest);

        //文件访问路径
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
        URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);

        // 关闭ossClient
        ossClient.shutdown();
        // 把上传到oss的路径返回
        return url.toString();
    }

    /**
     * 返回contentType
     *
     * @param FileNameExtension
     * @return
     */
    private static String getContentType(String FileNameExtension) {
        if (FileNameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FileNameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FileNameExtension.equalsIgnoreCase(".jpeg") ||
                FileNameExtension.equalsIgnoreCase(".jpg") ||
                FileNameExtension.equalsIgnoreCase(".png")
        ) {
            return "image/jpg";
        }
        return "image/jpg";
    }

    /**
     * @param path 相对于阿里云bucket存储空间的文件路径
     * @return java.util.List<java.lang.String> 该路径下所有文件的名称
     * @description 查询所有文件名称
     * 注：首先会获取根文件的名称
     */
    public static List<String> listFileName(String path) {
        List<String> res = new ArrayList<>();
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        // 设置prefix参数来获取fun目录下的所有文件。
        listObjectsRequest.setPrefix(path);

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 列出文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        return res;
    }

    /**
     * @param path 相对于阿里云bucket存储空间的文件路径
     * @return java.util.List<java.lang.String> 返回路径下所有文件的url
     * @description 查询所有文件的url
     * 注：首先会获取根文件夹的url，且根文件夹的url无法访问
     */
    public static List<String> listFileUrl(String path) {
        List<String> res = new ArrayList<>();

        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        // 设置prefix参数来获取fun目录下的所有文件。
        listObjectsRequest.setPrefix(path);

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 列出文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件。

        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            //文件访问路径
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
            URL url = ossClient.generatePresignedUrl(bucketName, objectSummary.getKey(), expiration);
            res.add(url.toString());
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        return res;
    }

    /**
     * @param objectName 相对于阿里云bucket存储空间的文件路径和文件名称
     * @return boolean 存在返回true，存在返回false
     * @description 判断文件是否存在
     */
    public static boolean isFileExist(String objectName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        boolean res = ossClient.doesObjectExist(bucketName, objectName);
        return res;
    }

    /**
     * @param objectName    相对于阿里云bucket存储空间的文件路径和文件名称
     * @param localFileName 下载的路径和文件名称
     * @description 通过文件名称（包括路径）下载文件
     */
    public static void downloadFile(String objectName, String localFileName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFileName));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * @param objectName 相对于阿里云bucket存储空间的文件路径和文件名称
     * @description 删除文件或目录
     */
    public static void delelteFile(String objectName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, objectName);
        ossClient.shutdown();
    }

    /**
     * @param keys 相对于阿里云bucket存储空间的文件路径和文件名称
     * @description 批量删除文件或目录
     */
    public static void deleteFiles(List<String> keys) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
        java.util.List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();

        ossClient.shutdown();
    }

    /**
     * @param folder 文件夹名称。注：文件夹名称后面需要添加“/”，否则创建的是文件，并非文件夹。
     * @return java.lang.String 相对于阿里云bucket存储空间的文件夹路径
     * @description 创建文件夹
     */
    public static String createFolder(String folder) {
        // 文件夹名
        final String keySuffixWithSlash = folder;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            // 创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            // 得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            ossClient.shutdown();
            return fileDir;
        }

        return keySuffixWithSlash;
    }

}
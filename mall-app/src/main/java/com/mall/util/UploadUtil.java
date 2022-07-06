package com.mall.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.mall.globel.Const;

/**
 * @author JngKang
 * @date 2022-07-05 17:49
 */
public class UploadUtil {
    public static String uploadImg(String base64, String fileName) {
        if (base64.indexOf("base64") > 0) {
            String[] dataArray = StrUtil.splitToArray(base64, "base64,");
            byte[] bytes = Base64.decode(dataArray[1]);
            String imgName = System.currentTimeMillis() + "_" + fileName;
            FileUtil.writeBytes(bytes, Const.IMG_SERVICE_PATH + imgName);
            String img = Const.IMG_SERVICE + imgName;
            return img;
        }
        return base64;
    }
}

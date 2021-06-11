package com.djh.shanjupay.common.constant;

import lombok.Data;
import lombok.Getter;

/**
 * 聚宇支付常量类
 *
 * @author MyMrDiao
 * @date 2021/05/12
 */
public interface ShanjuPayConstant {
    /**
     * 上传图片格式格式
     */
    String[] UPLOAD_FILE_TYPE = {".jpg", ".png", ".jpeg"};

    /**
     * 偏移变量，固定占8位字节
     */
    String IV_PARAMETER = "12345678";
    /**
     * 加密算法
     */
    String ALGORITHM = "DES";
    /**
     * 加密/解密算法-工作模式-填充模式
     */
    String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    /**
     * 默认编码
     */
    String CHARSET = "utf-8";
    /**
     * 秘钥
     */
    String KEY = "jyzh_token";
}

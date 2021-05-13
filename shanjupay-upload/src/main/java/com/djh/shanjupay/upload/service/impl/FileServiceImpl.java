package com.djh.shanjupay.upload.service.impl;

import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.upload.properties.QiniuProperty;
import com.djh.shanjupay.upload.util.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文件服务实现类
 *
 * @author MyMrDiao
 * @date 2021/05/13
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl {
    @Autowired
    private QiniuProperty qiniuProperty;


    /**
     * qiniu oss上传
     *
     * @param bytes    字节
     * @param fileName 文件名称
     * @return {@link String}
     */
    public String qiniuOssUpload (byte[] bytes, String fileName) {
        try {
            QiniuUtils.uploadQiniu(qiniuProperty.getAccessKey(), qiniuProperty.getSecretKey(), qiniuProperty.getBucket(), bytes, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_100106);
        }
        // 返回文件url
        return qiniuProperty.getQiniuUrl() + fileName;
    }
}
